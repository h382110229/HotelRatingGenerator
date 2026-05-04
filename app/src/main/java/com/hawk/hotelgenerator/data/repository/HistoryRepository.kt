package com.hawk.hotelgenerator.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.historyDataStore by preferencesDataStore(name = "history")

/**
 * 历史点评记录数据模型
 */
data class ReviewHistory(
    val id: String,
    val hotelName: String,
    val date: Long,
    val content: String
)

/**
 * 历史记录仓库 - 管理生成的点评历史
 */
@Singleton
class HistoryRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private val HISTORY_KEY = stringPreferencesKey("review_history")

    /**
     * 获取所有历史记录 (按时间倒序)
     */
    val allHistory: Flow<List<ReviewHistory>> = context.historyDataStore.data.map { prefs ->
        val json = prefs[HISTORY_KEY]
        if (json != null) {
            val type = object : TypeToken<List<ReviewHistory>>() {}.type
            gson.fromJson<List<ReviewHistory>>(json, type).sortedByDescending { it.date }
        } else {
            emptyList()
        }
    }

    /**
     * 添加新的历史记录
     */
    suspend fun addHistory(history: ReviewHistory) {
        context.historyDataStore.edit { prefs ->
            val currentJson = prefs[HISTORY_KEY]
            val currentList = if (currentJson != null) {
                val type = object : TypeToken<List<ReviewHistory>>() {}.type
                gson.fromJson<List<ReviewHistory>>(currentJson, type).toMutableList()
            } else {
                mutableListOf()
            }
            currentList.add(0, history) // 插入到最前面
            prefs[HISTORY_KEY] = gson.toJson(currentList.take(50)) // 仅保留最近 50 条
        }
    }

    /**
     * 清空历史
     */
    suspend fun clearHistory() {
        context.historyDataStore.edit { it.clear() }
    }
}

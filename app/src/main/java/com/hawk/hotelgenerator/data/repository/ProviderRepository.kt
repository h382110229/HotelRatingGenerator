package com.hawk.hotelgenerator.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.hawk.hotelgenerator.data.remote.Secrets
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

/**
 * AI 提供者配置数据模型
 */
data class ProviderConfig(
    val id: String = "longcat",
    val name: String = "LongCat-Flash-Lite",
    val apiKey: String = "",
    val baseUrl: String = Secrets.longcatBaseUrl,
    val model: String = Secrets.longcatModel
)

/**
 * 设置仓库 - 管理自定义模型与 API 配置
 */
@Singleton
class ProviderRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private val CONFIG_KEY = stringPreferencesKey("provider_config")

    /**
     * 获取当前配置
     */
    val currentConfig: Flow<ProviderConfig> = context.dataStore.data.map { prefs ->
        val json = prefs[CONFIG_KEY]
        if (json != null) {
            gson.fromJson(json, ProviderConfig::class.java)
        } else {
            ProviderConfig()
        }
    }

    /**
     * 保存新配置
     */
    suspend fun saveConfig(config: ProviderConfig) {
        context.dataStore.edit { prefs ->
            prefs[CONFIG_KEY] = gson.toJson(config)
        }
    }
}

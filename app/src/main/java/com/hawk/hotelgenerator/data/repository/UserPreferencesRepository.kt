package com.hawk.hotelgenerator.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userPrefsDataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val CUSTOM_ROOM_TYPES_KEY = stringSetPreferencesKey("custom_room_types")

    val customRoomTypes: Flow<Set<String>> = context.userPrefsDataStore.data.map { prefs ->
        prefs[CUSTOM_ROOM_TYPES_KEY] ?: emptySet()
    }

    suspend fun addCustomRoomType(roomType: String) {
        context.userPrefsDataStore.edit { prefs ->
            val current = prefs[CUSTOM_ROOM_TYPES_KEY] ?: emptySet()
            prefs[CUSTOM_ROOM_TYPES_KEY] = current + roomType
        }
    }
}

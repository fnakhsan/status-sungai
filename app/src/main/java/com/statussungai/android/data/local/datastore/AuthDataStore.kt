package com.statussungai.android.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthDataStore(private val dataStore: DataStore<Preferences>) {
    fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    fun getRole(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[ROLE_KEY]
        }
    }

    suspend fun saveRole(role: String) {
        dataStore.edit { preferences ->
            preferences[ROLE_KEY] = role
        }
    }

    suspend fun clearRole() {
        dataStore.edit { preferences ->
            preferences.remove(ROLE_KEY)
        }
    }

    fun getUserId(): Flow<Int?> {
        return dataStore.data.map { preferences ->
            preferences[USER_KEY]
        }
    }

    suspend fun saveUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[USER_KEY] = userId
        }
    }

    suspend fun clearUserId() {
        dataStore.edit { preferences ->
            preferences.remove(USER_KEY)
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val ROLE_KEY = stringPreferencesKey("role")
        private val USER_KEY = intPreferencesKey("user")
//        @Volatile
//        private var INSTANCE: AuthDataStore? = null
//        fun getInstance(dataStore: DataStore<Preferences>): AuthDataStore {
//            return INSTANCE ?: synchronized(this){
//                val instance = AuthDataStore(dataStore)
//                INSTANCE = instance
//                instance
//            }
//        }
    }
}
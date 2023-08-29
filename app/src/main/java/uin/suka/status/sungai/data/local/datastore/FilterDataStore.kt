package uin.suka.status.sungai.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilterDataStore private constructor(private val dataStore: DataStore<Preferences>) {
    fun getRiverId(): Flow<Int?> {
        return dataStore.data.map { preferences ->
            preferences[RIVER_KEY]
        }
    }

    suspend fun saveRiverId(riverId: Int) {
        dataStore.edit { preferences ->
            preferences[RIVER_KEY] = riverId
        }
    }

    suspend fun clearRiverId() {
        dataStore.edit { preferences ->
            preferences.remove(RIVER_KEY)
        }
    }

    fun getSeasonId(): Flow<Int?> {
        return dataStore.data.map { preferences ->
            preferences[SEASON_KEY]
        }
    }

    suspend fun saveSeasonId(seasonId: Int) {
        dataStore.edit { preferences ->
            preferences[SEASON_KEY] = seasonId
        }
    }

    suspend fun clearSeasonId() {
        dataStore.edit { preferences ->
            preferences.remove(SEASON_KEY)
        }
    }

    fun getYear(): Flow<Int?> {
        return dataStore.data.map { preferences ->
            preferences[YEAR_KEY]
        }
    }

    suspend fun saveYear(year: Int) {
        dataStore.edit { preferences ->
            preferences[YEAR_KEY] = year
        }
    }

    suspend fun clearYear() {
        dataStore.edit { preferences ->
            preferences.remove(YEAR_KEY)
        }
    }

    companion object {
        private val RIVER_KEY = intPreferencesKey("river")
        private val SEASON_KEY = intPreferencesKey("season")
        private val YEAR_KEY = intPreferencesKey("year")
        @Volatile
        private var INSTANCE: FilterDataStore? = null
        fun getInstance(dataStore: DataStore<Preferences>): FilterDataStore {
            return INSTANCE ?: synchronized(this){
                val instance = FilterDataStore(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
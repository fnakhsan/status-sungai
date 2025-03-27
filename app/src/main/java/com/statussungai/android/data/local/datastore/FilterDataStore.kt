package com.statussungai.android.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilterDataStore(private val dataStore: DataStore<Preferences>) {
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

    fun getSortAlphabetically(): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[SORT_ALPHABETICALLY_KEY]
        }
    }

    suspend fun saveSortAlphabetically(isAscending: Boolean) {
        dataStore.edit { preferences ->
            preferences[SORT_ALPHABETICALLY_KEY] = isAscending
        }
    }

    suspend fun clearSortAlphabetically() {
        dataStore.edit { preferences ->
            preferences.remove(SORT_ALPHABETICALLY_KEY)
        }
    }

    fun getSortDate(): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[SORT_DATE_KEY]
        }
    }

    suspend fun saveSortDate(isAscending: Boolean) {
        dataStore.edit { preferences ->
            preferences[SORT_DATE_KEY] = isAscending
        }
    }

    suspend fun clearSortDate() {
        dataStore.edit { preferences ->
            preferences.remove(SORT_DATE_KEY)
        }
    }

    companion object {
        private val RIVER_KEY = intPreferencesKey("river")
        private val SEASON_KEY = intPreferencesKey("season")
        private val YEAR_KEY = intPreferencesKey("year")
        private val SORT_ALPHABETICALLY_KEY = booleanPreferencesKey("alphabet")
        private val SORT_DATE_KEY = booleanPreferencesKey("date")
//        @Volatile
//        private var INSTANCE: FilterDataStore? = null
//        fun getInstance(dataStore: DataStore<Preferences>): FilterDataStore {
//            return INSTANCE ?: synchronized(this){
//                val instance = FilterDataStore(dataStore)
//                INSTANCE = instance
//                instance
//            }
//        }
    }
}
package uin.suka.status.sungai.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import uin.suka.status.sungai.data.Repository
import uin.suka.status.sungai.data.local.datastore.AuthDataStore
import uin.suka.status.sungai.data.local.datastore.FilterDataStore
import uin.suka.status.sungai.data.network.ApiConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")

object Injection {
    fun provideRepository(context: Context): Repository {
        return Repository.getInstance(
            ApiConfig.getApiService(),
            AuthDataStore.getInstance(context.dataStore),
            FilterDataStore.getInstance(context.dataStore)
        )
    }
}
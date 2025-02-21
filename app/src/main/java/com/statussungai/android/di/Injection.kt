package com.statussungai.android.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.statussungai.android.data.Repository
import com.statussungai.android.data.local.datastore.AuthDataStore
import com.statussungai.android.data.local.datastore.FilterDataStore
import com.statussungai.android.data.network.ApiConfig

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
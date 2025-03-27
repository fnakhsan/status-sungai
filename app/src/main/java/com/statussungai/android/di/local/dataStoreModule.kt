package com.statussungai.android.di.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.statussungai.android.data.local.datastore.AuthDataStore
import com.statussungai.android.data.local.datastore.FilterDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")

val dataStoreModule = module {
    single {
        AuthDataStore(androidContext().dataStore)
    }
    single {
        FilterDataStore(androidContext().dataStore)
    }
}
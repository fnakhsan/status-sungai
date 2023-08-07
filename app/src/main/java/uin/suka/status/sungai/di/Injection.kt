package uin.suka.status.sungai.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import uin.suka.status.sungai.core.utils.Const
import uin.suka.status.sungai.core.utils.Const.AUTH_TAG
import uin.suka.status.sungai.data.Repository
import uin.suka.status.sungai.data.local.datastore.AuthDataStore
import uin.suka.status.sungai.data.network.ApiConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")

object Injection {
    suspend fun provideRepository(context: Context): Repository {
        val token = AuthDataStore.getInstance(context.dataStore).getToken().firstOrNull()
        Log.d(AUTH_TAG, token.toString())
        return Repository.getInstance(
            ApiConfig.getApiService(token), AuthDataStore.getInstance(context.dataStore)
        )
    }
}
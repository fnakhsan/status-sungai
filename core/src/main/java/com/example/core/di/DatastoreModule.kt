package com.example.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.migrations.SharedPreferencesMigration
import com.example.core.data.local.datastore.FilterPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

//// Preferences DataStore
//private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)
//
//// Proto DataStore
//private val Context.userPreferencesStore by dataStore(
//    fileName = "user_prefs.pb",
//    serializer = PreferencesSerializer
//)

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {
    @Singleton
    @Provides
    fun provideProtoDataStore(@ApplicationContext appContext: Context): DataStore<FilterPreferences> {
        return DataStoreFactory.create(
            serializer = FilterPreferencesSerializer,
            produceFile = { appContext.dataStoreFile("user_prefs.pb") },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}
//
//@Module
//@InstallIn(SingletonComponent::class)
//class DatastoreModule {
//
//}
//
//
//abstract class UserPreferencesModule {
//
//    // binds instance of MyUserPreferencesRepository
//    @Binds
//    @Singleton
//    abstract fun bindUserPreferencesRepository(
//        myUserPreferencesRepository: MyUserPreferencesRepository
//    ): UserPreferencesRepository
//
//    companion object {
//
//        // provides instance of DataStore
//        @Provides
//        @Singleton
//        fun provideUserDataStorePreferences(
//            @ApplicationContext applicationContext: Context
//        ): DataStore<Preferences> {
//            return applicationContext.userDataStore
//        }
//    }
//}
package com.example.core.di

//// Preferences DataStore
//private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)
//
//// Proto DataStore
//private val Context.userPreferencesStore by dataStore(
//    fileName = DATA_STORE_FILE_NAME,
//    serializer = UserPreferencesSerializer
//)
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
package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.local.database.StatusSungaiDao
import com.example.core.data.local.database.StatusSungaiDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): StatusSungaiDatabase {
        return Room.databaseBuilder(
            context,
            StatusSungaiDatabase::class.java,
            "StatusSungai.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAnimeDao(database: StatusSungaiDatabase): StatusSungaiDao =
        database.statusSungaiDao()
}
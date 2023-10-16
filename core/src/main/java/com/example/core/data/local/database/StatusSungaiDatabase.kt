package com.example.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StatusSungaiDatabase::class], version = 1, exportSchema = false)
abstract class StatusSungaiDatabase : RoomDatabase() {

    abstract fun statusSungaiDao(): StatusSungaiDao

}
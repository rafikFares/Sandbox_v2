package com.example.sandbox.core.repository.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sandbox.core.repository.local.dao.ItemDao
import com.example.sandbox.core.repository.local.entity.ItemEntity

private const val ROOM_DB_VERSION = 1

@Database(
    entities = [ItemEntity::class],
    version = ROOM_DB_VERSION,
    autoMigrations = [],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}

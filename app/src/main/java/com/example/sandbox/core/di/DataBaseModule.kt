package com.example.sandbox.core.di

import androidx.room.Room
import com.example.sandbox.core.repository.local.dao.ItemDao
import com.example.sandbox.core.repository.local.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val ROOM_DB_NAME = "sandbox_v2_room"

val dataBaseModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            ROOM_DB_NAME
        ).build()
    }

    single<ItemDao> {
        val db: AppDatabase = get<AppDatabase>()
        db.itemDao()
    }
}

package com.demo.simplearithmetic.model.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PerformanceEntity::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun gamePerformanceDao(): PerformanceDao

    companion object {

        fun getInstance(appContext: Context): HistoryDatabase {
            return Room.databaseBuilder(
                appContext,
                HistoryDatabase::class.java,
                HistoryContract.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
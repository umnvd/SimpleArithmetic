package com.demo.simplearithmetic.model.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PerformanceDao {

    @Insert
    fun insert(performanceEntity: PerformanceEntity): Long

    @Query("SELECT * FROM game_performance_history ORDER BY timestamp DESC")
    fun getAll(): List<PerformanceEntity>

    @Query("DELETE FROM game_performance_history")
    fun deleteAll()
}
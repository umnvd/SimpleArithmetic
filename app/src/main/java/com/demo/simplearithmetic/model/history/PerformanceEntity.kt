package com.demo.simplearithmetic.model.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = HistoryContract.Performance.TABLE_NAME)
data class PerformanceEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = HistoryContract.Performance.COLUMN_ID)
    val _id: Long = 0,
    @ColumnInfo(name = HistoryContract.Performance.COLUMN_TIME_STAMP)
    val timestamp: Long,
    @ColumnInfo(name = HistoryContract.Performance.COLUMN_TOTAL_PROBLEMS)
    val totalProblems: Int,
    @ColumnInfo(name = HistoryContract.Performance.COLUMN_TOTAL_RIGHTS)
    val totalRights: Int,
    @ColumnInfo(name = HistoryContract.Performance.COLUMN_TOTAL_ACCURACY)
    val totalAccuracy: Float,
    @ColumnInfo(name = HistoryContract.Performance.COLUMN_SECONDS_TO_ANSWER)
    val secondsToAnswer: Float
)
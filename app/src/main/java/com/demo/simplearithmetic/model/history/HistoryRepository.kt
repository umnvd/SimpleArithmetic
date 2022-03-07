package com.demo.simplearithmetic.model.history

import com.demo.simplearithmetic.extensions.secondsToRank
import com.demo.simplearithmetic.extensions.timestampToDateTime
import com.demo.simplearithmetic.model.arithmetic.Operation
import com.demo.simplearithmetic.model.rating_game.Performance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryRepository(
    private val database: HistoryDatabase
) {
    suspend fun saveGamePerformance(performance: Performance) = withContext(Dispatchers.IO) {
        database.gamePerformanceDao().insert(performanceToEntity(performance))
    }

    suspend fun loadHistory(): List<Performance> = withContext(Dispatchers.IO) {
        database.gamePerformanceDao().getAll()
            .map(this@HistoryRepository::entityToPerformance)
    }

    // TODO: add on the screen
    suspend fun clearHistory() = withContext(Dispatchers.IO) {
        database.gamePerformanceDao().deleteAll()
    }

    // TODO: add on the screen
    suspend fun getAveragePerformance(): AveragePerformance = withContext(Dispatchers.IO) {
        val history = loadHistory()
        val totalProblems = history.map { it.problemCount }.sum()
        val totalRights = history.map { it.rightCount }.sum()
        val totalAccuracy = totalRights.toFloat() / totalProblems.toFloat()
        val totalSecondsToAnswer = history.map { it.secondsToAnswer }.sum() / history.size.toFloat()
        val totalRank = totalSecondsToAnswer.secondsToRank()

        AveragePerformance(
            accuracy = totalAccuracy,
            secondsToAnswer = totalSecondsToAnswer,
            rank = totalRank
        )
    }

    private fun performanceToEntity(performance: Performance): PerformanceEntity {
        return PerformanceEntity(
            timestamp = performance.timeStamp,
            totalProblems = performance.problemCount,
            totalRights = performance.rightCount,
            totalAccuracy = performance.accuracy,
            secondsToAnswer = performance.secondsToAnswer
        )
    }

    private fun entityToPerformance(performanceEntity: PerformanceEntity): Performance {
        val (date, time) = performanceEntity.timestamp.timestampToDateTime()
        val rank = performanceEntity.secondsToAnswer.secondsToRank()

        return Performance(
            timeStamp = performanceEntity.timestamp,
            date = date,
            time = time,
            problemCount = performanceEntity.totalProblems,
            rightCount = performanceEntity.totalRights,
            accuracy = performanceEntity.totalAccuracy,
            secondsToAnswer = performanceEntity.secondsToAnswer,
            rank = rank
        )
    }
}
package com.demo.simplearithmetic.di

import android.content.Context
import androidx.preference.PreferenceManager
import com.demo.simplearithmetic.model.arithmetic.ArithmeticProblemGenerator
import com.demo.simplearithmetic.model.arithmetic.Operation
import com.demo.simplearithmetic.model.game.Game
import com.demo.simplearithmetic.model.history.HistoryDatabase
import com.demo.simplearithmetic.model.history.HistoryRepository
import com.demo.simplearithmetic.model.rating_game.RatingGame

class AppComponent(private val appContext: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun setUpNewGame(): Game {
        val minValue = preferences.getInt(MIN_VALUE_KEY, DEFAULT_MIN_VALUE)
        val maxValue = preferences.getInt(MAX_VALUE_KEY, DEFAULT_MAX_VALUE)
        val operationsNames = preferences.getStringSet(OPERATIONS_KEY, null)
        val operations = getOperationsByNames(operationsNames) ?: Operation.values().toList()

        return Game(
            ArithmeticProblemGenerator(minValue, maxValue, operations)
        )

    }

    fun setUpNewRatingGame(): RatingGame {
        val timerLimit = preferences.getString(TIMER_KEY, null)?.toLong() ?: DEFAULT_TIME_LIMIT

        return RatingGame(
            ArithmeticProblemGenerator(
                DEFAULT_MIN_VALUE,
                DEFAULT_MAX_VALUE,
                Operation.values().toList()
            ),
            timerLimit = timerLimit
        )
    }

    fun setUpRepository(): HistoryRepository {
        return HistoryRepository(
            HistoryDatabase.getInstance(appContext)
        )
    }

    private fun getOperationsByNames(operationsNames: Set<String>?): List<Operation>? {
        return operationsNames?.map { Operation.valueOf(it.uppercase()) }
    }

    companion object {

        const val DEFAULT_MIN_VALUE = 1
        const val DEFAULT_MAX_VALUE = 100
        const val DEFAULT_TIME_LIMIT = 30000L

        const val OPERATIONS_KEY = "operations"
        const val MIN_VALUE_KEY = "min_value"
        const val MAX_VALUE_KEY = "max_value"
        const val TIMER_KEY = "timer"

    }
}
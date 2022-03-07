package com.demo.simplearithmetic.model.history

object HistoryContract {

    const val DATABASE_NAME = "history.db"

    object Performance {

        const val TABLE_NAME = "game_performance_history"

        const val COLUMN_ID = "_id"
        const val COLUMN_TIME_STAMP = "timestamp"
        const val COLUMN_TOTAL_PROBLEMS = "total_problems"
        const val COLUMN_TOTAL_RIGHTS = "total_rights"
        const val COLUMN_TOTAL_ACCURACY = "total_accuracy"
        const val COLUMN_SECONDS_TO_ANSWER = "seconds_to_answer"
    }
}
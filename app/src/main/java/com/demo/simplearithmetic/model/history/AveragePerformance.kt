package com.demo.simplearithmetic.model.history

import com.demo.simplearithmetic.model.rating_game.Rank

data class AveragePerformance(

    val accuracy: Float,
    val secondsToAnswer: Float,
    val rank: Rank
)

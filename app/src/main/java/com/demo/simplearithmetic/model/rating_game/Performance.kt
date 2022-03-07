package com.demo.simplearithmetic.model.rating_game

data class Performance(

    val timeStamp: Long,
    val date: String,
    val time: String,
    val rightCount: Int,
    val problemCount: Int,
    val accuracy: Float,
    val secondsToAnswer: Float,
    val rank: Rank
)

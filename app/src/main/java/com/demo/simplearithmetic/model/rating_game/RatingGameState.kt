package com.demo.simplearithmetic.model.rating_game

sealed class RatingGameState {
    class Started(val timeLeft: String) : RatingGameState()
    class Finished(val performance: Performance) : RatingGameState()
}

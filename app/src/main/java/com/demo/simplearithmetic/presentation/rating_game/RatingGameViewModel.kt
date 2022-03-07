package com.demo.simplearithmetic.presentation.rating_game

import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.demo.simplearithmetic.model.history.HistoryRepository
import com.demo.simplearithmetic.model.rating_game.Performance
import com.demo.simplearithmetic.model.rating_game.RatingGame
import com.demo.simplearithmetic.model.rating_game.RatingGameState
import com.demo.simplearithmetic.presentation.game.GameViewModel
import kotlinx.coroutines.launch

class RatingGameViewModel(
    private val ratingGame: RatingGame,
    private val historyRepository: HistoryRepository
) : GameViewModel(ratingGame) {

    val score = Transformations.distinctUntilChanged(ratingGame.score)
    val gameState = Transformations.map(ratingGame.ratingGameState) { state ->
        if (state is RatingGameState.Finished) {
            saveResult(state.performance)
        }
        state
    }

    fun startGame() = ratingGame.start()

    fun stopGame() = ratingGame.stop()

    private fun saveResult(performance: Performance) {
        viewModelScope.launch {
            historyRepository.saveGamePerformance(performance)
        }
    }

}
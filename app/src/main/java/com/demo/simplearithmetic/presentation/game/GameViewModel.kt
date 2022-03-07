package com.demo.simplearithmetic.presentation.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.simplearithmetic.model.game.CheckState
import com.demo.simplearithmetic.model.game.Game
import com.demo.simplearithmetic.model.game.GameTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class GameViewModel(
    private val game: Game
) : ViewModel() {

    private val _checkState: MutableLiveData<CheckState> = MutableLiveData()
    val checkState: LiveData<CheckState> get() = _checkState

    private val _task: MutableLiveData<GameTask> = MutableLiveData()
    val task: LiveData<GameTask> get() = _task

    init {
        viewModelScope.launch(Dispatchers.Main) {
            _task.value = game.newTask()
        }
    }

    open fun onAnswerClick(selectedAnswerId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            if (game.checkAnswer(selectedAnswerId)) {
                _checkState.value = CheckState.RightAnswer(selectedAnswerId)
            } else {
                _checkState.value =
                    CheckState.WrongAnswer(selectedAnswerId, game.getRightAnswerId())
            }
            delay(ANSWER_DISPLAY_DURATION) // to display the correct answer
            _checkState.value = CheckState.Checked

            _task.value = game.newTask()
        }
    }

    companion object {
        private const val ANSWER_DISPLAY_DURATION = 333L
    }
}
package com.demo.simplearithmetic.model.rating_game

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.simplearithmetic.extensions.secondsToRank
import com.demo.simplearithmetic.extensions.timestampToDateTime
import com.demo.simplearithmetic.model.arithmetic.ArithmeticProblemGenerator
import com.demo.simplearithmetic.model.game.Game
import com.demo.simplearithmetic.model.game.GameTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RatingGame(
    generator: ArithmeticProblemGenerator,
    private val timerLimit: Long
) : Game(generator) {

    private var startTimestamp = 0L

    private var problemCount: Int = 0

    private var rightCount: Int = 0

    private val _score = MutableLiveData(rightCount.toString())
    val score: LiveData<String> get() = _score

    private val _ratingGameState =
        MutableLiveData<RatingGameState>()
    val ratingGameState: LiveData<RatingGameState> get() = _ratingGameState

    private var isLastTask = false
    private val timer = object : CountDownTimer(timerLimit, 1000) {
        override fun onTick(time: Long) {
            if (time <= Rank.S.secondsToAnswer) {
                isLastTask = true
            }
            _ratingGameState.value = RatingGameState.Started(formatTime(time))
        }

        override fun onFinish() {
            _ratingGameState.value = RatingGameState.Finished(createResult())
        }
    }

    override suspend fun newTask(): GameTask = withContext(Dispatchers.Default) {
        if (!isLastTask) {
            problem = generator.generateProblem()
            problemCount++
        }
        val expression = "${problem.firstNumber} ${problem.operation.sign} ${problem.secondNumber}"
        val answers = problem.answers.map(Int::toString)

        GameTask(
            expression = expression,
            answers = answers
        )


    }

    override fun checkAnswer(selectedAnswerId: Int): Boolean {
        val isAnswerRight = super.checkAnswer(selectedAnswerId)
        if (isAnswerRight && !isLastTask) {
            rightCount++
            _score.value = rightCount.toString()
        }
        return isAnswerRight
    }

    fun start() {
        if (_ratingGameState.value is RatingGameState.Started) return
        startTimestamp = System.currentTimeMillis()
        problemCount = 0
        rightCount = 0
        _score.value = rightCount.toString()
        _ratingGameState.value = RatingGameState.Started(formatTime(timerLimit))
        isLastTask = false
        timer.start()
    }

    fun stop() {
        timer.cancel()
    }

    fun createResult(): Performance {

        val (date, time) = startTimestamp.timestampToDateTime()
        val accuracy = if (problemCount != 0) {
            rightCount.toFloat() / problemCount.toFloat()
        } else {
            1f
        }
        val secondsToAnswer = if (rightCount != 0) {
            (timerLimit / 1000).toFloat() / rightCount.toFloat()
        } else {
            (timerLimit / 1000).toFloat()
        }
        val rank = secondsToAnswer.secondsToRank()

        return Performance(
            timeStamp = startTimestamp,
            date = date,
            time = time,
            problemCount = problemCount,
            rightCount = rightCount,
            accuracy = accuracy,
            secondsToAnswer = secondsToAnswer,
            rank = rank
        )
    }

    private fun formatTime(timeInMillis: Long): String {
        val timeInSeconds = timeInMillis / 1000
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

}
package com.demo.simplearithmetic.model.game

sealed class CheckState {
    class RightAnswer(val selectedAnswerId: Int) : CheckState()
    class WrongAnswer(val selectedAnswerId: Int, val rightAnswerId: Int) : CheckState()
    object Checked : CheckState()
}

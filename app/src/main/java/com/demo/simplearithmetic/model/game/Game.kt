package com.demo.simplearithmetic.model.game

import com.demo.simplearithmetic.model.arithmetic.ArithmeticProblem
import com.demo.simplearithmetic.model.arithmetic.ArithmeticProblemGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class Game(
    protected val generator: ArithmeticProblemGenerator
) {
    protected lateinit var problem: ArithmeticProblem

    open suspend fun newTask(): GameTask = withContext(Dispatchers.Default) {
        problem = generator.generateProblem()
        val expression = "${problem.firstNumber} ${problem.operation.sign} ${problem.secondNumber}"
        val answers = problem.answers.map(Int::toString)

        GameTask(
            expression = expression,
            answers = answers
        )
    }

    open fun checkAnswer(selectedAnswerId: Int): Boolean {
        return selectedAnswerId == problem.rightAnswerId
    }

    fun getRightAnswerId(): Int {
        return problem.rightAnswerId
    }
}
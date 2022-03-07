package com.demo.simplearithmetic.model.arithmetic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class ArithmeticProblemGenerator(
    private val minValue: Int,
    private val maxValue: Int,
    private val operations: List<Operation>
) {

    private val random = Random(System.currentTimeMillis())
    private val shift = if (maxValue / 20 >= 5) maxValue / 20 else 5


    suspend fun generateProblem(): ArithmeticProblem = withContext(Dispatchers.Default) {
        when (operations[random.nextInt(operations.size)]) {
            Operation.ADDITION -> createAdditionProblem()
            Operation.SUBTRACTION -> createSubtractionProblem()
            Operation.MULTIPLICATION -> createMultiplicationProblem()
            Operation.DIVISION -> createDivisionProblem()
        }
    }

    private fun createAdditionProblem(): ArithmeticProblem {
        val firstNumber = random.nextInt(minValue, maxValue / 2 + 1)
        val secondNumber = random.nextInt(minValue, maxValue / 2 + 1)
        val rightAnswer = firstNumber + secondNumber
        val answers = createAnswers(rightAnswer)
        val rightAnswerId = answers.indexOf(rightAnswer)

        return ArithmeticProblem(
            operation = Operation.ADDITION,
            firstNumber = firstNumber,
            secondNumber = secondNumber,
            answers = answers,
            rightAnswerId = rightAnswerId
        )
    }

    private fun createSubtractionProblem(): ArithmeticProblem {
        val rightAnswer = random.nextInt(minValue, maxValue / 2 + 1)
        val secondNumber = random.nextInt(minValue, maxValue / 2 + 1)
        val firstNumber = rightAnswer + secondNumber
        val answers = createAnswers(rightAnswer)
        val rightAnswerId = answers.indexOf(rightAnswer)

        return ArithmeticProblem(
            operation = Operation.SUBTRACTION,
            firstNumber = firstNumber,
            secondNumber = secondNumber,
            answers = answers,
            rightAnswerId = rightAnswerId
        )
    }

    private fun createMultiplicationProblem(): ArithmeticProblem {
        val multMinValue = if (minValue > 2) minValue else 2
        var rightAnswer = random.nextInt(multMinValue * 2, maxValue + 1)
        val firstNumber = random.nextInt(multMinValue, rightAnswer / 2 + 1)
        val secondNumber = rightAnswer / firstNumber
        rightAnswer = firstNumber * secondNumber
        val answers = createAnswers(rightAnswer)
        val rightAnswerId = answers.indexOf(rightAnswer)

        return ArithmeticProblem(
            operation = Operation.MULTIPLICATION,
            firstNumber = firstNumber,
            secondNumber = secondNumber,
            answers = answers,
            rightAnswerId = rightAnswerId
        )
    }

    private fun createDivisionProblem(): ArithmeticProblem {
        val divMinValue = if (minValue > 2) minValue else 2
        var firstNumber = random.nextInt(divMinValue * 2, maxValue + 1)
        val secondNumber = random.nextInt(divMinValue, firstNumber / 2 + 1)
        val rightAnswer = firstNumber / secondNumber
        firstNumber = secondNumber * rightAnswer
        val answers = createAnswers(rightAnswer)
        val rightAnswerId = answers.indexOf(rightAnswer)

        return ArithmeticProblem(
            operation = Operation.DIVISION,
            firstNumber = firstNumber,
            secondNumber = secondNumber,
            answers = answers,
            rightAnswerId = rightAnswerId
        )
    }

    private fun createAnswers(rightAnswer: Int): List<Int> {

        val answers = mutableListOf(rightAnswer)
        while (answers.size < 4) {
            val wrongAnswer = when {
                rightAnswer <= shift -> {
                    val leftShift = rightAnswer - 1
                    val rightShift = shift * 2 - leftShift
                    random.nextInt(rightAnswer - leftShift, rightAnswer + rightShift + 1)
                }
                rightAnswer + shift > maxValue -> {
                    val rightShift = maxValue - rightAnswer
                    val leftShift = shift * 2 - rightShift
                    random.nextInt(rightAnswer - leftShift, rightAnswer + rightShift + 1)
                }
                else -> {
                    random.nextInt(rightAnswer - shift, rightAnswer + shift + 1)
                }
            }
            if (!answers.contains(wrongAnswer)) {
                answers.add(wrongAnswer)
            }
        }
        return answers.shuffled()
    }
}
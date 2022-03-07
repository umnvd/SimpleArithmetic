package com.demo.simplearithmetic.model.arithmetic

data class ArithmeticProblem(
    val operation: Operation,
    val firstNumber: Int,
    val secondNumber: Int,
    val answers: List<Int>,
    val rightAnswerId: Int
)
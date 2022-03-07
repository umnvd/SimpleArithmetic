package com.demo.simplearithmetic.presentation.game

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.demo.simplearithmetic.R
import com.demo.simplearithmetic.di.appViewModels
import com.demo.simplearithmetic.model.game.CheckState
import com.demo.simplearithmetic.model.game.GameTask

open class GameFragment : Fragment(R.layout.fragment_game) {

    protected open val viewModel: GameViewModel by appViewModels()

    private var expression: TextView? = null
    private var answers: List<TextView?> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        setUpObservers(this.viewLifecycleOwner)
        setUpListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearViews()
    }

    protected open fun setUpObservers(owner: LifecycleOwner) {
        viewModel.task.observe(owner, this::showNewTask)
        viewModel.checkState.observe(owner, this::showCheckState)
    }

    protected open fun initViews(parent: View) {
        expression = parent.findViewById(R.id.tv_main_expression)
        answers = listOf(
            parent.findViewById(R.id.b_main_answer_1),
            parent.findViewById(R.id.b_main_answer_2),
            parent.findViewById(R.id.b_main_answer_3),
            parent.findViewById(R.id.b_main_answer_4)
        )
    }

    protected open fun clearViews() {
        expression = null
        answers = emptyList()
    }

    private fun setUpListeners() {
        for ((id, answer) in answers.withIndex()) {
            answer?.setOnClickListener {
                viewModel.onAnswerClick(id)
            }
        }
    }

    private fun showCheckState(checkState: CheckState) {
        when (checkState) {
            is CheckState.RightAnswer -> showRightAnswer(checkState.selectedAnswerId)
            is CheckState.WrongAnswer -> showWrongAnswer(
                checkState.selectedAnswerId,
                checkState.rightAnswerId
            )
            is CheckState.Checked -> resetAnswersColors()
        }
    }

    private fun showNewTask(task: GameTask) {
        expression?.text = task.expression
        for ((id, answer) in task.answers.withIndex()) {
            answers[id]?.text = answer
        }
    }

    private fun resetAnswersColors() {
        for (answer in answers) {
            answer?.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.gray))
        }

        setAnswersIsClickable(true)
    }

    private fun showRightAnswer(selectedAnswerId: Int) {
        setAnswersIsClickable(false)

        answers[selectedAnswerId]?.setBackgroundColor(
            ContextCompat.getColor(this.requireContext(), R.color.green)
        )
    }

    private fun showWrongAnswer(selectedAnswerId: Int, rightAnswerId: Int) {
        setAnswersIsClickable(false)

        answers[selectedAnswerId]?.setBackgroundColor(
            ContextCompat.getColor(this.requireContext(), R.color.red)
        )
        answers[rightAnswerId]?.setBackgroundColor(
            ContextCompat.getColor(this.requireContext(), R.color.green)
        )
    }

    private fun setAnswersIsClickable(isClickable: Boolean) {
        for (answer in answers) {
            answer?.isClickable = isClickable
        }
    }
}
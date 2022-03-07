package com.demo.simplearithmetic.presentation.rating_game

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.lifecycle.LifecycleOwner
import com.demo.simplearithmetic.R
import com.demo.simplearithmetic.di.appViewModels
import com.demo.simplearithmetic.model.rating_game.Performance
import com.demo.simplearithmetic.model.rating_game.RatingGameState
import com.demo.simplearithmetic.presentation.HomeFragment
import com.demo.simplearithmetic.presentation.Navigator
import com.demo.simplearithmetic.presentation.game.GameFragment

class RatingGameFragment : GameFragment() {

    override val viewModel: RatingGameViewModel by appViewModels()
    private var navigator: Navigator? = null

    private var score: TextView? = null
    private var timer: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = activity as Navigator
        activity?.onBackPressedDispatcher?.addCallback(this) {
            viewModel.stopGame()
            isEnabled = false
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navigator = null
    }

    override fun onStart() {
        super.onStart()
        viewModel.startGame()
    }

    override fun initViews(parent: View) {
        super.initViews(parent)
        score = parent.findViewById(R.id.tv_main_score)
        timer = parent.findViewById(R.id.tv_main_timer)
        score?.visibility = View.VISIBLE
        timer?.visibility = View.VISIBLE
    }

    override fun clearViews() {
        super.clearViews()
        score = null
        timer = null
    }

    override fun setUpObservers(owner: LifecycleOwner) {
        super.setUpObservers(owner)
        viewModel.score.observe(owner, this::showNewScore)
        viewModel.gameState.observe(owner, this::onChangeGameState)
    }

    private fun showNewScore(newScore: String) {
        score?.text = newScore
    }

    private fun onChangeGameState(ratingGameState: RatingGameState) {
        when (ratingGameState) {
            is RatingGameState.Started -> updateTimer(ratingGameState.timeLeft)
            is RatingGameState.Finished -> showResult(ratingGameState.performance)
        }
    }

    private fun updateTimer(newTime: String) {
        Log.e("TIME", newTime)
        timer?.text = newTime
    }

    private fun showResult(performance: Performance) {
        val message = String.format(
            "Right/total: %d/%d\nAccuracy: %d%%\nSeconds to answer: %.2f\nRank: %s",
            performance.rightCount,
            performance.problemCount,
            (performance.accuracy * 100).toInt(),
            performance.secondsToAnswer,
            performance.rank
        )
        val resultDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("Game over")
            setMessage(message)
            setPositiveButton("Play again") { dialog, id ->
                viewModel.startGame()
            }
            setNegativeButton("To home") { dialog, id ->
                navigator?.navigateTo(HomeFragment())
            }
            setOnCancelListener {
                navigator?.navigateTo(HomeFragment())
            }
        }.create()
        resultDialog.show()
    }
}
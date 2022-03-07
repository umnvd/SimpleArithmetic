package com.demo.simplearithmetic.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.demo.simplearithmetic.R
import com.demo.simplearithmetic.presentation.game.GameFragment
import com.demo.simplearithmetic.presentation.history.HistoryFragment
import com.demo.simplearithmetic.presentation.rating_game.RatingGameFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var trainingGameButton: Button? = null
    private var ratingGameButton: Button? = null
    private var historyButton: Button? = null
    private var settingsButton: Button? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setUpListeners()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearViews()
    }

    private fun setUpListeners() {
        val navigator = requireActivity() as? Navigator
        trainingGameButton?.setOnClickListener {
            navigator?.navigateTo(GameFragment())
        }
        ratingGameButton?.setOnClickListener {
            navigator?.navigateTo(RatingGameFragment())
        }
        historyButton?.setOnClickListener {
            navigator?.navigateTo(HistoryFragment())
        }
        settingsButton?.setOnClickListener {
            navigator?.navigateTo(SettingsFragment())
        }
    }

    private fun initViews(parent: View) {
        trainingGameButton = parent.findViewById(R.id.b_home_training)
        ratingGameButton = parent.findViewById(R.id.b_home_rating)
        historyButton = parent.findViewById(R.id.b_home_history)
        settingsButton = parent.findViewById(R.id.b_home_settings)
    }

    private fun clearViews() {
        trainingGameButton = null
        ratingGameButton = null
        historyButton = null
        settingsButton = null
    }


}
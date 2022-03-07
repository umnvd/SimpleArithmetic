package com.demo.simplearithmetic.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.simplearithmetic.SimpleArithmeticApp
import com.demo.simplearithmetic.presentation.game.GameViewModel
import com.demo.simplearithmetic.presentation.history.HistoryViewModel
import com.demo.simplearithmetic.presentation.rating_game.RatingGameViewModel

class ViewModelsFactory(private val appComponent: AppComponent) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            RatingGameViewModel::class.java -> RatingGameViewModel(
                appComponent.setUpNewRatingGame(), appComponent.setUpRepository()
            ) as T
            GameViewModel::class.java -> GameViewModel(appComponent.setUpNewGame()) as T
            HistoryViewModel::class.java -> HistoryViewModel(appComponent.setUpRepository()) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class ${modelClass.simpleName}")
        }
    }

}

inline fun <reified T : ViewModel> Fragment.appViewModels() = viewModels<T> {
    ViewModelsFactory(
        (requireContext().applicationContext as SimpleArithmeticApp).appComponent
    )
}
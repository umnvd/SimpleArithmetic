package com.demo.simplearithmetic.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.simplearithmetic.model.history.HistoryRepository
import com.demo.simplearithmetic.model.rating_game.Performance
import kotlinx.coroutines.launch

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {

    private val _performanceList = MutableLiveData<List<Performance>>(null)
    val performanceList: LiveData<List<Performance>> get() = _performanceList

    fun updateHistory() {
        viewModelScope.launch {
            _performanceList.value = historyRepository.loadHistory()
        }
    }

}
package com.demo.simplearithmetic.presentation.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.simplearithmetic.R
import com.demo.simplearithmetic.di.appViewModels

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val historyViewModel: HistoryViewModel by appViewModels()
    private var historyRecycler: RecyclerView? = null
    private var performanceAdapter: PerformanceAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyRecycler = view.findViewById(R.id.rv_history)
        performanceAdapter = PerformanceAdapter()
        historyRecycler?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = performanceAdapter
        }

        historyViewModel.performanceList.observe(this.viewLifecycleOwner) {
            performanceAdapter?.submitList(it)
        }

    }

    override fun onStart() {
        super.onStart()
        historyViewModel.updateHistory()
    }

}
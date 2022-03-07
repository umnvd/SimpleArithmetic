package com.demo.simplearithmetic.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.demo.simplearithmetic.R
import com.demo.simplearithmetic.model.rating_game.Performance

class PerformanceAdapter :
    ListAdapter<Performance, PerformanceViewHolder>(PERFORMANCE_DIFF_CALLBACK) {

    init {
        this.setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).timeStamp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformanceViewHolder {
        return PerformanceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.performance_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PerformanceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        private val PERFORMANCE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Performance>() {
            override fun areItemsTheSame(oldItem: Performance, newItem: Performance): Boolean {
                return oldItem.timeStamp == newItem.timeStamp
            }

            override fun areContentsTheSame(oldItem: Performance, newItem: Performance): Boolean {
                return oldItem == newItem
            }
        }
    }
}
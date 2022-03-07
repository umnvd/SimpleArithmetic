package com.demo.simplearithmetic.presentation.history

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.simplearithmetic.R
import com.demo.simplearithmetic.model.rating_game.Performance

class PerformanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val date: TextView = itemView.findViewById(R.id.tv_history_date)
    private val time: TextView = itemView.findViewById(R.id.tv_history_time)
    private val accuracy: TextView = itemView.findViewById(R.id.tv_history_accuracy)
    private val answersToProblems: TextView =
        itemView.findViewById(R.id.tv_history_answers_to_problems)
    private val secondsToAnswer: TextView = itemView.findViewById(R.id.tv_history_seconds_to_answer)
    private val rank: TextView = itemView.findViewById(R.id.tv_history_rank)

    fun bind(performance: Performance) {
        date.text = performance.date
        time.text = performance.time
        accuracy.text = ((performance.accuracy * 100).toInt() / 100).toString()
        answersToProblems.text = String.format(
            "%d/%d",
            performance.rightCount,
            performance.problemCount
        )
        secondsToAnswer.text = String.format(
            "%.2f",
            performance.secondsToAnswer
        )
        rank.text = performance.rank.name
    }
}
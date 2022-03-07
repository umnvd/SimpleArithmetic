package com.demo.simplearithmetic.extensions

import androidx.preference.MultiSelectListPreference
import com.demo.simplearithmetic.model.rating_game.Rank
import java.text.SimpleDateFormat
import java.util.*

fun MultiSelectListPreference.setSummaryFromSelection(selection: Set<String>) {
    summary = selection.joinToString(", ") { entries[findIndexOfValue(it)] }
}

fun Float.secondsToRank(): Rank {
    return when {
        this < Rank.S.secondsToAnswer -> Rank.S
        this < Rank.A.secondsToAnswer -> Rank.A
        this < Rank.B.secondsToAnswer -> Rank.B
        this < Rank.C.secondsToAnswer -> Rank.C
        else -> Rank.D
    }
}

fun Long.timestampToDateTime(): Pair<String, String> {
    return Pair(
        SimpleDateFormat.getDateInstance().format(Date(this)),
        SimpleDateFormat.getTimeInstance().format(Date(this))
    )
}
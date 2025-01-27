package com.suno.android.sunointerview.uitls

import android.annotation.SuppressLint

object StringUtil {

    fun formatNumberToCompactString(number: Int): String {
        return when {
            number >= 1_000_000 -> "${number / 1_000_000}M"
            number >= 1_000 -> "${number / 1_000}k"
            else -> number.toString()
        }
    }

    @SuppressLint("DefaultLocale")
    fun formatDuration(durationSeconds: Double): String {
        val totalSeconds = Math.round(durationSeconds).toInt()
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60

        return String.format("%d:%02d", minutes, seconds)
    }
}
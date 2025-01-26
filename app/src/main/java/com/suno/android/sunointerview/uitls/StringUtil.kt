package com.suno.android.sunointerview.uitls

object StringUtil {
    fun formatNumberToCompactString(number: Int): String {
        return when {
            number >= 1_000_000 -> "${number / 1_000_000}M"
            number >= 1_000 -> "${number / 1_000}k"
            else -> number.toString()
        }
    }
}
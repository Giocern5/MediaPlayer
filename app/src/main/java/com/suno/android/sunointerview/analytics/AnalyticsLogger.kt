package com.suno.android.sunointerview.analytics

interface AnalyticsLogger {
    fun logSong(sourceName: String)
    fun logAppOpen()
}
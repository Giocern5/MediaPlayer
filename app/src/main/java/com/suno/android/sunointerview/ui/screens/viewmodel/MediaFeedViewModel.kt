package com.suno.android.sunointerview.ui.screens.viewmodel

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.suno.android.sunointerview.data.repository.MediaFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import androidx.paging.cachedIn
import androidx.lifecycle.*
import com.suno.android.sunointerview.analytics.AnalyticsLogger
import com.suno.android.sunointerview.data.SongFeedData

@HiltViewModel
class MediaFeedViewModel @Inject constructor(
    repo: MediaFeedRepository,
    private val analyticsLogger: AnalyticsLogger,
    ): ViewModel() {

    companion object {
        const val TAG = "MediaFeedViewModel"
    }

    val pagingFlow: Flow<PagingData<SongFeedData>> = repo.getSongs()
        .cachedIn(viewModelScope)

    private val mediaPlayer = MediaPlayer()

    fun startSong(songUrl: String, songName: String) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(songUrl)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
            }
            Log.e(TAG, "Starting song")
            analyticsLogger.logSong(songName)
        } catch (e: Exception) {
            Log.e(TAG, "Error starting song: ${e.message}")
        }
    }

    fun resetSong() {
        mediaPlayer.seekTo(0)
        mediaPlayer.start()
        Log.e(TAG, "Restarting song")
    }

    fun toggleSong() {
        when{
            mediaPlayer.isPlaying -> {
                mediaPlayer.pause()
                Log.e(TAG, "Pausing song")
            } else -> {
                mediaPlayer.start()
                Log.e(TAG, "Continuing song")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
        Log.e(TAG, "MediaPlayer released")
    }
}
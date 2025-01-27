package com.suno.android.sunointerview.ui.screens.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.suno.android.sunointerview.data.repository.MediaFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import androidx.paging.cachedIn
import androidx.lifecycle.*
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.suno.android.sunointerview.analytics.AnalyticsLogger
import com.suno.android.sunointerview.data.SongFeedData

@HiltViewModel
class MediaFeedViewModel @Inject constructor(
    repo: MediaFeedRepository,
    private val analyticsLogger: AnalyticsLogger,
    private val exoPlayer: ExoPlayer
) : ViewModel() {

    companion object {
        const val TAG = "MediaFeedViewModel"
    }

    val pagingFlow: Flow<PagingData<SongFeedData>> = repo.getSongs()
        .cachedIn(viewModelScope)

    fun startSong(songUrl: String, songName: String) {
        try {
            // Prepare the media item
            val mediaItem = MediaItem.fromUri(songUrl)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()

            Log.e(TAG, "Starting song")
            analyticsLogger.logSong(songName)
        } catch (e: Exception) {
            Log.e(TAG, "Error starting song: ${e.message}")
        }
    }

    fun resetSong() {
        try {
            exoPlayer.seekTo(0)
            exoPlayer.play()
            Log.e(TAG, "Restarting song")
        } catch (e: Exception) {
            Log.e(TAG, "Error resetting song: ${e.message}")
        }
    }

    fun toggleSong() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            Log.e(TAG, "Pausing song")
        } else {
            exoPlayer.play()
            Log.e(TAG, "Continuing song")
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
        Log.e(TAG, "ExoPlayer released")
    }
}
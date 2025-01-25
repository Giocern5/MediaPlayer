package com.suno.android.sunointerview.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.suno.android.sunointerview.data.Song
import com.suno.android.sunointerview.data.paging.MediaFeedPagingSource
import com.suno.android.sunointerview.network.MediaService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val pageSize = 10

class MediaFeedRepositoryImpl @Inject constructor(private val mediaService: MediaService): MediaFeedRepository {

    companion object {
        const val TAG = "MediaFeedRepositoryImpl"
    }

    override fun getSongs(): Flow<PagingData<Song>> {
        Log.e(TAG, "Fetching songs")
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { MediaFeedPagingSource(mediaService) }
        ).flow
    }

}
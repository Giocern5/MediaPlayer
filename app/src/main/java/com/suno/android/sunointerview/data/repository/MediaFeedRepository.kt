package com.suno.android.sunointerview.data.repository

import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData
import com.suno.android.sunointerview.data.SongFeedData

interface MediaFeedRepository {
    fun getSongs(): Flow<PagingData<SongFeedData>>
}
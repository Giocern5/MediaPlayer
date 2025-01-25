package com.suno.android.sunointerview.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.suno.android.sunointerview.data.repository.MediaFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import androidx.paging.cachedIn
import androidx.lifecycle.*
import com.suno.android.sunointerview.data.SongFeedData

@HiltViewModel
class MediaFeedViewModel @Inject constructor(
    repo: MediaFeedRepository
): ViewModel() {

    companion object {
        const val TAG = "MediaFeedViewModel"
    }

    val pagingFlow: Flow<PagingData<SongFeedData>> = repo.getSongs()
        .cachedIn(viewModelScope)

}
package com.suno.android.sunointerview.ui.screens.feed

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.suno.android.sunointerview.ui.screens.viewmodel.MediaFeedViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun MediaFeedScreen(viewModel: MediaFeedViewModel) {

    val songs = viewModel.pagingFlow.collectAsLazyPagingItems()


    Text(
        text = "Hello World!",
    )
}
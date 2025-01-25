package com.suno.android.sunointerview.ui.screens.feed

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.suno.android.sunointerview.ui.screens.viewmodel.MediaFeedViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import android.util.Log
import coil.compose.AsyncImage
import com.suno.android.sunointerview.data.SongFeedData

@Composable
fun MediaFeedScreen(viewModel: MediaFeedViewModel) {

    val songs = viewModel.pagingFlow.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        SongsList( songs )
    }

}

@Composable
fun SongsList(songs: LazyPagingItems<SongFeedData>) {

    when (songs.loadState.refresh) {
        is LoadState.Error -> {
        CenterContainer("Ooops, something went wrong! Please check internet or try again")
    }
        is LoadState.Loading -> { }
        is LoadState.NotLoading -> {
            LazyColumn {
                items(songs.itemCount) { index ->
                    songs[index]?.let {
                        SongItem(song = it)
                    }
                }
            }
        }
    }

}

@Composable
fun SongItem(song: SongFeedData) {

    Log.e("URL", song.imageLargeUrl)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = song.imageLargeUrl,
            contentDescription = "Article Image",
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        )
    }
}

@Composable
fun CenterContainer(message: String) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
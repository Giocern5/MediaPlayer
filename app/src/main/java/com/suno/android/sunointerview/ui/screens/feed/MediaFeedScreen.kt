package com.suno.android.sunointerview.ui.screens.feed

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.suno.android.sunointerview.data.SongFeedData


// App crashed on screen rotation, look into it
// Fix button section,
// if song ends, swipe to next or maybe restart?
// update like button
// add dislike button
// see what buttons are available as Material3 does not have all
// update naming conventions
// play / pause button
// progress bar for song?

@Composable
fun MediaFeedScreen(viewModel: MediaFeedViewModel) {

    val songs = viewModel.pagingFlow.collectAsLazyPagingItems()
    val sharedMediaPlayer = remember { MediaPlayer() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
            color = MaterialTheme.colorScheme.surfaceContainer
        ),) {
        SongsList(songs = songs, mediaPlayer = sharedMediaPlayer)
    }

    DisposableEffect(Unit) {
        onDispose {
            sharedMediaPlayer.release()
        }
    }
}

@Composable
fun SongsList(songs: LazyPagingItems<SongFeedData>, mediaPlayer: MediaPlayer) {
    when (songs.loadState.refresh) {
        is LoadState.Error -> {
            CenterContainer("Ooops, something went wrong! Please check internet or try again")
        }
        is LoadState.Loading -> {}
        is LoadState.NotLoading -> {
            //used for snapping behavior
            val listState = rememberLazyListState()
            val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

            LazyColumn(
                state = listState,
                flingBehavior = flingBehavior
            ) {
                items(songs.itemCount) { index ->
                    songs[index]?.let {
                        SongItem(song = it, mediaPlayer = mediaPlayer)
                    }
                }
            }
        }
    }
}

@Composable
fun SongItem(song: SongFeedData, mediaPlayer: MediaPlayer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f / 16f)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = song.imageLargeUrl,
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Blur effect box at the bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Extend the blur area
                    .align(Alignment.BottomCenter)
                    .blur(64.dp) // Stronger blur
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent, // Top of the gradient
                                Color.Black.copy(alpha = 0.7f) // Bottom of the gradient
                            )
                        )
                    )
            )
                {}

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    UserInfoSection(song)
                    SongControls(songUrl = song.audioUrl, mediaPlayer = mediaPlayer)
                }

                ButtonSection(song.isLiked)
            }
        }
    }
}

@Composable
fun ButtonSection(isLiked: Boolean) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        IconButton(onClick = { /* toast? */ }) {
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = "Like",
                tint =  if(isLiked) Color.Red else  MaterialTheme.colorScheme.background
            )
        }

        IconButton(onClick = { /* toast? */ }) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = MaterialTheme.colorScheme.background
            )
        }

        IconButton(onClick = { /* toast? */ }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Composable
fun UserInfoSection(song: SongFeedData) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = song.title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            AsyncImage(
                model = song.avatarImageUrl,
                contentDescription = "Avatar Image",
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(
                text = song.displayName,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun SongControls(songUrl: String, mediaPlayer: MediaPlayer) {

    var isPlaying by remember { mutableStateOf(false) }

    DisposableEffect(songUrl) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(songUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
            isPlaying = true
        }
        onDispose {
            mediaPlayer.stop()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        IconButton(
            onClick = {
                mediaPlayer.seekTo(0)
                if (!isPlaying) {
                    mediaPlayer.start()
                    isPlaying = true
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Restart",
                tint = Color.White
            )
        }

        IconButton(
            onClick = {
                if (isPlaying) {
                    mediaPlayer.pause()
                    isPlaying = false
                } else {
                    mediaPlayer.start()
                    isPlaying = true
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play button",
                tint = Color.White
            )
        }
    }
}

@Composable
fun CenterContainer(message: String) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

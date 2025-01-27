package com.suno.android.sunointerview.ui.screens.feed

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
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.ThumbUp
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.suno.android.sunointerview.data.SongFeedData

import  com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.suno.android.sunointerview.uitls.StringUtil

// if song ends, swipe to next or maybe restart?
// update naming conventions
// per logs, songs still play on fast swipe

@Composable
fun MediaFeedScreen(viewModel: MediaFeedViewModel) {
    val songs = viewModel.pagingFlow.collectAsLazyPagingItems()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            isRefreshing = true
            songs.refresh()
            isRefreshing = false
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
        ) {
            SongsList(songs = songs, viewModel = viewModel)
        }
    }
}


@Composable
fun SongsList(songs: LazyPagingItems<SongFeedData>, viewModel: MediaFeedViewModel) {
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
                        SongItem(song = it, viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun SongItem(song: SongFeedData, viewModel: MediaFeedViewModel) {
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
            // Background image
            AsyncImage(
                model = song.imageLargeUrl,
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Blur effect at the bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.BottomCenter)
                    .blur(64.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.99f)
                            )
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)
                ) {
                    UserInfoSection(song)
                    SongControls(songUrl = song.audioUrl, songLength = song.songLength, viewModel = viewModel)
                }

                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)
                ) {
                    ButtonSection(song.isLiked, song.isTrashed, song.likes)
                }
            }
        }
    }
}


@Composable
fun ButtonSection(isLiked: Boolean, isTrashed: Boolean, likes: Int) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        IconButton(onClick = { /* toast? */ }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Rounded.ThumbUp,
                    contentDescription = "Like",
                    tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.background
                )
                Text(
                    text = StringUtil.formatNumberToCompactString(likes),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
        //Created dislike button as it is not a default icons
        IconButton(onClick = { /* toast? */ }) {
            Icon(
                imageVector = Icons.Rounded.ThumbUp,
                contentDescription = "Dislike",
                tint = if (isTrashed) Color.Red else MaterialTheme.colorScheme.background,
                modifier = Modifier.graphicsLayer(rotationZ = 180f, rotationY = 180f)
            )
        }

        IconButton(onClick = { /* toast? */ }) {
            Icon(
                imageVector = Icons.Rounded.Share,
                contentDescription = "Share",
                tint = MaterialTheme.colorScheme.background
            )
        }

        IconButton(onClick = { /* toast? */ }) {
            Icon(
                imageVector = Icons.Rounded.Menu,
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
            when {
                song.avatarImageUrl.isEmpty() -> {
                    Icon(
                        imageVector = Icons.Rounded.AccountBox,
                        contentDescription = "Default Avatar",
                        tint = MaterialTheme.colorScheme.background
                    )
                }
                else -> {
                    AsyncImage(
                        model = song.avatarImageUrl,
                        contentDescription = "Avatar Image",
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            }

            Text(
                text = song.displayName,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun SongControls(songUrl: String, songLength: Double, viewModel: MediaFeedViewModel) {

    DisposableEffect(songUrl) {
        viewModel.startSong(songUrl)
        onDispose {
            viewModel.stopSong()
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
                viewModel.resetSong()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = "Restart",
                tint = MaterialTheme.colorScheme.background
            )
        }

        IconButton(
            onClick = {
                viewModel.toggleSong()
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = "Play button",
                tint = MaterialTheme.colorScheme.background
            )
        }

        Text(
            text = StringUtil.formatDuration(songLength),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
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

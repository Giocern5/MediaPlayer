package com.suno.android.sunointerview.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.suno.android.sunointerview.ui.screens.feed.MediaFeedScreen
import com.suno.android.sunointerview.ui.screens.viewmodel.MediaFeedViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MediaApp() {
    val navController = rememberNavController()
    val viewModel =  hiltViewModel<MediaFeedViewModel>()

    NavHost(navController = navController, startDestination = Screen.MediaFeedScreen.route) {
        composable(
            route = Screen.MediaFeedScreen.route){
            MediaFeedScreen(viewModel)
        }
    }

}
package com.suno.android.sunointerview.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.suno.android.sunointerview.ui.screens.feed.MediaFeedScreen

@Composable
fun MediaApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MediaFeedScreen.route) {
        composable(
            route = Screen.MediaFeedScreen.route){
            MediaFeedScreen()
        }
    }

}
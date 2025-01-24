package com.suno.android.sunointerview.ui.screens.navigation

sealed class Screen(val route: String) {
    data object MediaFeedScreen : Screen("MediaFeedScreen")
}
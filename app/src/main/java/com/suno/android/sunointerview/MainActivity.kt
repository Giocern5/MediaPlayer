package com.suno.android.sunointerview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.suno.android.sunointerview.ui.screens.navigation.MediaApp
import com.suno.android.sunointerview.ui.theme.SunoInterviewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SunoInterviewTheme {
                MediaApp()
            }
        }
    }
}
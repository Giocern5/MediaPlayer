package com.suno.android.sunointerview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.FirebaseApp
import com.suno.android.sunointerview.analytics.AnalyticsLogger
import com.suno.android.sunointerview.ui.screens.navigation.MediaApp
import com.suno.android.sunointerview.ui.theme.SunoInterviewTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Due to time constraints, not setting up dashboard but just missing google-services.json
    @Inject
    lateinit var analyticsLogger: AnalyticsLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        analyticsLogger.logAppOpen()
        enableEdgeToEdge()
        setContent {
            SunoInterviewTheme(dynamicColor = false) {
                MediaApp()
            }
        }
    }
}
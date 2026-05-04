package com.hawk.hotelgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hawk.hotelgenerator.ui.screens.home.HomeScreen
import com.hawk.hotelgenerator.ui.theme.HotelRatingTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * 主 Activity - 应用程序入口
 * 负责设置 Compose 主题并导航到主界面
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotelRatingTheme {
                var currentScreen by remember { mutableStateOf("home") }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {
                        "home" -> HomeScreen(
                            viewModel = hiltViewModel(),
                            onNavigateToHistory = { currentScreen = "history" },
                            onNavigateToSettings = { currentScreen = "settings" }
                        )
                        "history" -> com.hawk.hotelgenerator.ui.screens.history.HistoryScreen(
                            viewModel = hiltViewModel(),
                            onNavigateToHome = { currentScreen = "home" },
                            onNavigateToSettings = { currentScreen = "settings" }
                        )
                        "settings" -> com.hawk.hotelgenerator.ui.screens.settings.SettingsScreen(
                            viewModel = hiltViewModel(),
                            onNavigateToHome = { currentScreen = "home" },
                            onNavigateToHistory = { currentScreen = "history" }
                        )
                    }
                }
            }
        }
    }
}

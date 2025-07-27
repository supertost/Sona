package com.example.sona

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sona.ui.theme.SpotiControlTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )


        enableEdgeToEdge()
        setContent {
            SpotiControlTheme {
                SpotiControl()
            }
        }
    }
}



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SpotiControl() {


    val context = LocalContext.current
    val controller = remember { SpotifySessionController(context) }
    val isPlaying = controller.isPlaying

    val versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "Unknown"


    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        AnimatedNavHost(navController = navController, startDestination = "player", modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color.Black),
            enterTransition = { fadeIn() + slideInHorizontally() },
            exitTransition = { fadeOut() + slideOutHorizontally() },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { it }) }) {

            composable (route = "player") { PlayerScreen(navController, isPlaying) }

            composable (route = "settings") { SettingsScreen(navController, versionName) }

        }

    }

}
package com.example.game

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.game.ui.theme.GameTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            GameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login"){
                            LoginView(onLogin = {
                                navController.navigate("game_start")
                            })
                        }

                        composable("game_start") {
                            GameHome(onPlayClick = {
                                navController.navigate("game_screen")
                            },
                                onHighscoreClick = {
                                    navController.navigate("highscore")
                                })
                        }

                        composable("game_screen") {
                            GameScreenView(onGameOver = {
                                navController.navigate("gameover_screen")
                            })
                        }

                        composable("gameover_screen") {
                            GameoverScreen(onPlayGame = {
                                navController.navigate("game_screen")
                            },
                                onMainMenu = {
                                    navController.navigate("game_start")
                                })
                        }

                        composable("highscore"){
                            HighscoreScreen(onReturn = {
                                navController.navigate("game_start")
                            })
                        }
                    }
                }
            }
        }
    }
}
package com.example.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun GameScreenView(onGameOver: () -> Unit = {}) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val density = configuration.densityDpi / 160f
    val screenWidthPx = screenWidth * density
    val screenHeightPx = screenHeight * density

    AndroidView(factory = { context ->
        GameView(context = context, width = screenWidthPx.toInt(), height = screenHeightPx.toInt(), onGameOver = onGameOver)
    }, update = {
        //it.resume()
    })

    //Log.d("STILL ALIVE", "MAIN THREAD HAS REACHED THIS POINT")

    //Log.d("Idk", "What's happening.")
}
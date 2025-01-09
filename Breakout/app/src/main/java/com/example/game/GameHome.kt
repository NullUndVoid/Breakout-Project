package com.example.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GameHome(modifier: Modifier = Modifier, onPlayClick: () -> Unit = {}, onHighscoreClick: () -> Unit = {}) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Image(painter = painterResource(id = R.drawable.bg),
            contentDescription = "", modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)

        Column(modifier = Modifier.padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painter = painterResource(id = R.drawable.playnow),
                contentDescription = "Play Now",
                modifier = Modifier.width(200.dp)
                    .height(64.dp)
                    .clickable { onPlayClick() },
                contentScale = ContentScale.FillBounds)

            Spacer(modifier = Modifier.height(16.dp))

            Image(painter = painterResource(id = R.drawable.highscore),
                contentDescription = "Highscore",
                modifier = Modifier.width(160.dp).height(80.dp)
                    .clickable { onHighscoreClick() },
                contentScale = ContentScale.FillBounds)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameHomePreview() {
    GameHome()
}
package com.example.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.ui.theme.GameTheme

@Composable
fun GameoverScreen(modifier: Modifier = Modifier, onPlayGame: () -> Unit = {}, onMainMenu: () -> Unit = {}){
    //Log.d("Hit", "Game Over Screen Reached")
    Column(modifier = modifier.fillMaxSize().background(color = Color.Black).wrapContentHeight(align = Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Game Over",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = Color.White
        )

        Row(modifier = modifier){
            TextButton (modifier = Modifier.size(width = 120.dp, height = 50.dp).fillMaxSize(), onClick = { onPlayGame() }, shape = RoundedCornerShape(25), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB00000))){
                Text(text = "Play Again",
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.width(25.dp))

            TextButton (modifier = Modifier.size(width = 120.dp, height = 50.dp).fillMaxSize(), onClick = { onMainMenu() }, shape = RoundedCornerShape(25), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB00000))){
                Text(text = "Main Menu",
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GameoverScreenPreview(){
    GameTheme {
        GameoverScreen()
    }
}
package com.example.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.game.ui.theme.GameTheme

@Composable
fun HighscoreScreen(modifier: Modifier = Modifier, onReturn: () -> Unit = {}) {
    val viewModel : HighscoreView = viewModel()
    val state = viewModel.state.value

    if(state.isLoading){
        Column (modifier = modifier.fillMaxSize().background(color = Color.Black).wrapContentHeight(align = Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally){
            CircularProgressIndicator()
        }
    }

    else{
        Column (modifier = modifier){
            Column (modifier = modifier.fillMaxWidth().background(color = Color.Black)){
                TextButton (modifier = Modifier.size(width = 60.dp, height = 60.dp).fillMaxSize(), onClick = { onReturn() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)){
                    Text(text = "X",
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        color = Color.White
                    )
                }
            }

            Column(modifier = modifier.fillMaxSize().background(color = Color.Black).wrapContentHeight(align = Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = if(state.highestscore.error != null) state.highestscore.error!! else "Highscore: ${state.highestscore.score}",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    color = Color.White
                )
            }
        }
    }

    LaunchedEffect (key1 = true){
        viewModel.getHighscore()
    }
}

@Preview(showBackground = true)
@Composable
fun HighscoreScreenPreview(){
    GameTheme {
        HighscoreScreen()
    }
}
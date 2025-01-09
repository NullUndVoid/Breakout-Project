package com.example.game

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.game.models.Highscore
import com.example.game.repo.HighscoreRepo

data class HighscoreFinal(
    var highestscore: Highscore = Highscore(),
    var isLoading: Boolean = false,
)

class HighscoreView: ViewModel(){
    var state = mutableStateOf(HighscoreFinal(isLoading = true))
        private set

    fun getHighscore(){
        HighscoreRepo.queryHighscore { highscore ->
            state.value = state.value.copy(
                highestscore = highscore
            )

            state.value.isLoading = false
        }
    }
}
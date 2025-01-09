package com.example.game.repo

import android.util.Log
import com.example.game.models.Highscore
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

object HighscoreRepo {
    val db = Firebase.firestore
    val auth = Firebase.auth

    fun queryHighscore(onResult : (Highscore) -> Unit ){
        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        var finalHighscore = Highscore()

        db.collection("highscores")
            .whereEqualTo("userId", userId)
            .get()

            .addOnSuccessListener { highscores ->
                Log.d("wtf", "aight")
                for (highscore in highscores) {
                    Log.d("TAG", "${highscore.id} => ${highscore.data}")

                    val currentHighscore = highscore.toObject(Highscore::class.java)
                    if((currentHighscore.score ?: 0) > (finalHighscore.score ?: 0)){
                        finalHighscore = highscore.toObject(Highscore::class.java)
                    }

                }

                onResult(finalHighscore)
            }

            .addOnFailureListener { exception ->
                //Log.w("TAG", "Error getting documents: ", exception)
                finalHighscore.score = 0
                finalHighscore.error = exception.message

                onResult(finalHighscore)
            }
    }

    fun updateHighscore(score: Int = 0){
        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        var finalHighscore = Highscore()
        var finalHighscoreId = ""

        db.collection("highscores")
            .whereEqualTo("userId", userId)
            .get()

            .addOnSuccessListener { highscores ->
                Log.d("wtf", "aight")
                for (highscore in highscores) {
                    Log.d("TAG", "${highscore.id} => ${highscore.data}")

                    val currentHighscore = highscore.toObject(Highscore::class.java)
                    if((currentHighscore.score ?: 0) > (finalHighscore.score ?: 0)){
                        finalHighscore = highscore.toObject(Highscore::class.java)
                        finalHighscoreId = highscore.id
                    }

                }

                if(score > (finalHighscore.score ?: 0)){
                    db.collection("highscores")
                        .document(finalHighscoreId)
                        .update("score", score)
                }
            }

            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }

    }

    fun logout() {
        val auth = Firebase.auth
        val currentUser = auth.signOut()
    }
}
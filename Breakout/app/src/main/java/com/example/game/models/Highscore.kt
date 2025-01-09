package com.example.game.models

class Highscore (
    var userId : String?,
    var name : String?,
    var score : Int?,
    var error: String?) {

    constructor() : this(null,null,0, null)
}
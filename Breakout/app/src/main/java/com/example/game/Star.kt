package com.example.game

import java.util.Random

class Star(width: Int, height: Int) {

    private val generator = Random()

    private var maxX = width
    private var maxY = height
    var x = generator.nextInt(maxX)
    var y = generator.nextInt(maxY)
    private var speed = generator.nextInt(15) + 1
    private var minX = 0
    private var minY = 0

    fun update() {
        x -= speed

        if (x < 0) {
            x = maxX
            y = Random().nextInt(maxY)
            speed = generator.nextInt(15) + 1
        }
    }

    val starWidth : Int
        get() {
            return generator.nextInt(10) + 1
        }

    init {
        minX = 0
        minY = 0
    }
}
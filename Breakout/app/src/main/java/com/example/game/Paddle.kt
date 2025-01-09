package com.example.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Paddle(context: Context, width: Int, height: Int) {
    var bitmap : Bitmap

    var x : Int = 0
    var y : Int = 0
    private var max_x : Int = 0
    private var min_x : Int = 0

    var detectCollision : Rect

    init {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.platform)
        bitmap = Bitmap.createScaledBitmap(bitmap, 600, 200, false)

        x = (width / 2) - (bitmap.width / 2)
        y = height - bitmap.height // Bottom spacing
        max_x = (width - bitmap.width / 2)
        min_x = (bitmap.width / 2)

        detectCollision = Rect(x, y, bitmap.width, bitmap.height)
    }

    fun update(x_pos : Int) {
        if(x_pos in min_x..max_x)
            x = x_pos - (bitmap.width / 2)

        detectCollision.left = x
        detectCollision.top = y
        detectCollision.right = x + bitmap.width
        detectCollision.bottom = y + bitmap.height
    }
}
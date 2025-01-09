package com.example.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Brick(context: Context, xpos: Int, ypos: Int) {
    var bitmap: Bitmap

    var x : Int = 0
    var y : Int = 0
    var visible : Boolean = true

    var detectCollision : Rect

    init {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.alienship)
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 150, false)

        x = xpos
        y = ypos

        detectCollision = Rect(x, y, x + bitmap.width, y + bitmap.height)
    }
}
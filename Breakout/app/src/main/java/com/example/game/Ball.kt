package com.example.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Ball(context: Context, var width: Int, var height: Int) {
    var bitmap: Bitmap

    var x : Int = 0
    var y : Int = 0
    var x_max : Int = 0
    var x_min : Int = 0
    var y_min : Int = 0
    var y_max : Int = 0
    var vx : Int = 0
    var vy : Int = 0

    var detectCollision : Rect

    init {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.energyball)
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false)

        reset()

        x_max = (width - bitmap.width)
        x_min = 0
        y_min = 0
        y_max = (height - bitmap.height)

        detectCollision = Rect(x, y, bitmap.width, bitmap.height)
    }

    fun update(): Boolean {
        x += vx
        y += vy

        if (x >= x_max)
            vx = -12

        else if(x <= x_min)
            vx = 12

        else if (y > y_max + bitmap.height){
            return false
        }

        else if(y <= y_min)
            vy = 18

        detectCollision.left = x
        detectCollision.top = y
        detectCollision.right = x + bitmap.width
        detectCollision.bottom = y + bitmap.height

        return true
    }

    fun reset() {
        x = (width / 2) - (bitmap.width / 2)
        y = (height / 2) + (bitmap.height / 2)
        vx = (arrayOf<Int>(-20, 20).random()) //Either move to the left or to the right idc
        vy = 18 // Vertical velocity
    }
}
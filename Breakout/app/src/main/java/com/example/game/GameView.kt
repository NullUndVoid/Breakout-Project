package com.example.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.game.repo.HighscoreRepo

class GameView : SurfaceView, Runnable {

    private var playing : Boolean = false
    private var gameThread : Thread? = null
    private lateinit var surfaceHolder : SurfaceHolder
    private lateinit var canvas: Canvas

    private lateinit var paint : Paint
    private var stars = arrayListOf<Star>()
    private var bricks = arrayListOf<Brick>()
    private var brick_template = Brick(context, 0, 0)

    private lateinit var paddle : Paddle
    private lateinit var ball : Ball

    private var score = 0
    private var onGameOverHolder = {}

    private fun init(context: Context, width: Int, height: Int, onGameOver: () -> Unit = {}) {
        surfaceHolder = holder
        paint = Paint()
        onGameOverHolder = onGameOver

        for(i in 0..100) {
            stars.add(Star(width, height))
        }

        for(row in 0..3) {
            for(col in 0..7) {
                bricks.add(
                    Brick(
                        context,
                        (col * (brick_template.bitmap.width + 5) + 40),
                        (row * (brick_template.bitmap.height + 5) + 30)
                    )
                )
            }
        }

        //for(brick in bricks)
            //Log.d("Bomba", brick.x.toString() + " " + brick.y.toString() + " - " + brick.detectCollision)

        paddle = Paddle(context, width, height)
        ball = Ball(context, width, height)

        playing = true
        gameThread = Thread(this)
        gameThread?.start()
    }

    constructor(context: Context?, width: Int, height: Int, onGameOver: () -> Unit = {}) : super(context) {
        init(context!!, width, height, onGameOver)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context!!, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context!!, 0, 0)
    }

    /*fun pause() {
        //playing = false
        Log.d("Hit", "Pre Thread Join")
        Log.d("I HATE KOTLIN AND ANDROID", gameThread?.isAlive.toString())
        gameThread?.join()

        Log.d("Hit", "Pre Main Looper")
        val handler = Handler(Looper.getMainLooper())
        Log.d("Hit", "Pre Game Over")
        handler.post({
            onGameOverHolder()
        })
        //Log.d("Hit", "Post Game Over")
        //onGameOverHolder()
        //CoroutineScope(Dispatchers.Main).launch { onGameOverHolder() }
        //Log.d("Idk", "Hit")
        //Log.d("Idk", "Hit2")
    }*/

    override fun run() {
        while(playing) {
            //Log.d("Pre Update", "Pre Update")
            update()
            //Log.d("Pre Draw", "Pre Draw")
            draw()
            //Log.d("Pre Control", "Pre Control")
            control()
            //Log.d("Passed", "Passed")
            //Log.d("Passed", playing.toString())
        }

        val handler = Handler(Looper.getMainLooper())
        handler.post{
            HighscoreRepo.updateHighscore(score)
            onGameOverHolder()
        }

        //Log.d("Final", "Final")
    }

    private fun update() {
        for(s in stars) {
            s.update()
        }

        if(!ball.update()){
            //pause()
            playing = false
        }

        if(Rect.intersects(ball.detectCollision, paddle.detectCollision)){
            ball.vy = -15

            val hit_pos : Double = ((ball.x.toDouble() + (ball.bitmap.width.toDouble() / 2.0)) - paddle.x.toDouble()) / paddle.bitmap.width.toDouble()

            ball.vx = if(hit_pos < 0.5) -12 else 12
            //Log.d("DIR: ", ball.vx.toString())
        }

        var bricksexist = false
        //Log.d("Bomba", ball.x.toString() + " " + ball.y.toString())
        for(brick in bricks) {
            //Log.d("I hate my life", Rect.intersects(ball.detectCollision, brick.detectCollision).toString())
            //Log.d("I hate my life2", ball.x.toString() + " " + ball.y.toString())
            //Log.d("HOW", brick.visible.toString())
            if(brick.visible) {
                bricksexist = true
                if(Rect.intersects(ball.detectCollision, brick.detectCollision)) {
                    brick.visible = false
                    ball.vy *= -1
                    score += 8
                    break
                }
                //Log.d("WTF", ball.detectCollision.toString() + " " + brick.detectCollision.toString())
            }
        }

        //Log.d("I HATE ANDROID", (!bricksexist).toString())
        if(!bricksexist){
            for(brick in bricks){
                brick.visible = true
            }

            ball.reset()
        }
    }

    private fun draw() {
        if(surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas()

            canvas.drawColor(Color.BLACK)

            paint.color = Color.YELLOW

            for(star in stars) {
                paint.strokeWidth = star.starWidth.toFloat()
                canvas.drawPoint(star.x.toFloat(), star.y.toFloat(), paint)
            }

            canvas.drawBitmap(ball.bitmap, ball.x.toFloat(), ball.y.toFloat(), paint)
            canvas.drawBitmap(paddle.bitmap, paddle.x.toFloat(), paddle.y.toFloat(), paint)

            for(brick in bricks) {
                if(brick.visible)
                    canvas.drawBitmap(brick.bitmap, brick.x.toFloat(), brick.y.toFloat(), paint)
            }

            paint.color = Color.WHITE
            paint.textSize = 60F

            canvas.drawText("Score: $score", 0F, (height - 20).toFloat(), paint)

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun control() {
        Thread.sleep(20)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_MOVE -> {
                paddle.update(event.x.toInt())
            }
        }

        return true
    }
}
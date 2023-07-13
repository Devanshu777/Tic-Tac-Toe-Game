package com.example.tiktaktoegame

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    var gameActive = true




    // Player representation
    // 0 - X
    // 1 - O
    var activePlayer = 0
    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

    // State meanings:
    //    0 - X
    //    1 - O
    //    2 - Null

    // put all win positions in a 2D array
    var winPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun playerTap(view: View) {
        val img: ImageView = view as ImageView
        val tappedImage = img.tag.toString().toInt()

        if (!gameActive) {
            gameReset(view)
            return
        }

        if (gameState[tappedImage] == 2) {
            gameState[tappedImage] = activePlayer
            img.translationY = -1000f

            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x)
                activePlayer = 1
                val status = findViewById<TextView>(R.id.status)
                status.text = "O's Turn - Tap to play"
            } else {
                img.setImageResource(R.drawable.o)
                activePlayer = 0
                val status = findViewById<TextView>(R.id.status)
                status.text = "X's Turn - Tap to play"
            }

            img.animate().translationYBy(1000f).duration = 300
        }

        var flag = 0
        if (checkWin()) {
            gameActive = false
            flag = 1
            val status = findViewById<TextView>(R.id.status)
            val winnerStr = if (activePlayer == 1) "X has won" else "O has won"
            status.text = winnerStr
        } else if (isDraw()) {
            gameActive = false
            val status = findViewById<TextView>(R.id.status)
            status.text = "Match Draw"
        }
    }

    private fun checkWin(): Boolean {
        for (winPosition in winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                gameState[winPosition[1]] == gameState[winPosition[2]] &&
                gameState[winPosition[0]] != 2
            ) {
                return true
            }
        }
        return false
    }

    private fun isDraw(): Boolean {
        return !gameState.contains(2)
    }

    fun gameReset(view: View?) {
        gameActive = true
        activePlayer = 0
        gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

        val imageViews = arrayOf(
            findViewById<ImageView>(R.id.imageView0),
            findViewById<ImageView>(R.id.imageView1),
            findViewById<ImageView>(R.id.imageView2),
            findViewById<ImageView>(R.id.imageView3),
            findViewById<ImageView>(R.id.imageView4),
            findViewById<ImageView>(R.id.imageView5),
            findViewById<ImageView>(R.id.imageView6),
            findViewById<ImageView>(R.id.imageView7),
            findViewById<ImageView>(R.id.imageView8)
        )

        for (imageView in imageViews) {
            imageView.setImageResource(0)
        }

        val status = findViewById<TextView>(R.id.status)
        status.text = "X's Turn - Tap to play"
    }
}

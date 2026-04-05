package com.example.dicerollxml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val dice1 = findViewById<ImageView>(R.id.dice1)
        val dice2 = findViewById<ImageView>(R.id.dice2)
        val button = findViewById<Button>(R.id.rollButton)
        val message = findViewById<TextView>(R.id.messageText)

        dice1.setImageResource(R.drawable.dice_0)
        dice2.setImageResource(R.drawable.dice_0)

        button.setOnClickListener {
            val result1 = (1..6).random()
            val result2 = (1..6).random()

            dice1.setImageResource(getImageResource(result1))
            dice2.setImageResource(getImageResource(result2))

            message.visibility = TextView.VISIBLE
            if (result1 == result2) {
                message.text = "Selamat, anda dapat dadu double!"
            } else {
                message.text = "Anda belum beruntung!"
            }
        }
    }

    private fun getImageResource(result: Int): Int {
        return when (result) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }
}
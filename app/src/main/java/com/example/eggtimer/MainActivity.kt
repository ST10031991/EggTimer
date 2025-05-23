package com.example.eggtimer

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eggImage = findViewById<ImageView>(R.id.eggImage)
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        eggImage.startAnimation(animation)

        val softBoiledButton = findViewById<Button>(R.id.softBoiledButton)
        val mediumBoiledButton = findViewById<Button>(R.id.mediumBoiledButton)
        val hardBoiledButton = findViewById<Button>(R.id.hardBoiledButton)

        // Set click listeners
        softBoiledButton.setOnClickListener {
            startCountdown(300)  // 5 minutes (300 seconds)
        }

        mediumBoiledButton.setOnClickListener {
            startCountdown(420)  // 7 minutes (420 seconds)
        }

        hardBoiledButton.setOnClickListener {
            startCountdown(600)  // 10 minutes (600 seconds)
        }
    }

    private fun startCountdown(seconds: Int) {
        val intent = Intent(this, CountdownActivity::class.java)
        intent.putExtra("TIMER_SECONDS", seconds)
        startActivity(intent)
    }

}
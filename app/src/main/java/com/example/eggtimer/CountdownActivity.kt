package com.example.eggtimer

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CountdownActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null
    private lateinit var eggImage: ImageView
    private lateinit var timerText: TextView
    private lateinit var cancelButton: Button
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var progressBar: ProgressBar
    private var vibrator: Vibrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)

        // Initialize UI elements
        eggImage = findViewById(R.id.eggImage)
        timerText = findViewById(R.id.timerText)
        cancelButton = findViewById(R.id.cancelButton)
        progressBar = findViewById(R.id.progressBar)

        // Initialize vibrator
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Get the selected time from MainActivity
        val timeInSeconds = intent.getIntExtra("TIMER_SECONDS", 0)
        timerText.text = "Time: $timeInSeconds seconds" // ✅ Fixed: Now using initialized timerText

        progressBar.max = timeInSeconds
        progressBar.progress = timeInSeconds

        startCountdown(timeInSeconds)

        // Cancel button functionality
        cancelButton.setOnClickListener {
            countDownTimer?.cancel()
            finish()
        }
    }

    private fun startCountdown(timeInSeconds: Int) {
        countDownTimer = object : CountDownTimer(timeInSeconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / 1000).toInt()
                timerText.text = "Time: $secondsLeft seconds"

                // ✅ Update progress bar
                progressBar.progress = secondsLeft

                updateEggAnimation(secondsLeft, timeInSeconds)
            }


            override fun onFinish() {
                timerText.text = "Your eggs are ready!"
                eggImage.setImageResource(R.drawable.egg_hard)
                playAlarmSound()

                // ✅ Vibrate for 1 second
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator?.vibrate(1000)
                }
            }
        }.start()
    }

    private fun updateEggAnimation(secondsLeft: Int, totalSeconds: Int) {
        val progress = 1.0 - (secondsLeft.toFloat() / totalSeconds)

        when {
            progress < 0.3 -> eggImage.setImageResource(R.drawable.egg_soft)
            progress < 0.7 -> eggImage.setImageResource(R.drawable.egg_medium)
            else -> eggImage.setImageResource(R.drawable.egg_hard)
        }
    }

    private fun playAlarmSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.timer_sound)
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
        mediaPlayer?.release()
    }
}

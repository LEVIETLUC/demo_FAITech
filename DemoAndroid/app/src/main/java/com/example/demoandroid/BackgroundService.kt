package com.example.demoandroid

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.demoandroid.activities.ForegroundServiceActivity

class BackgroundService : Service() {

    private var countDownTimer: CountDownTimer? = null


    override fun onCreate() {
        super.onCreate()
        Log.d("BackgroundService", "onCreate")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d("BackgroundService", "onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("BackgroundService", "onStartCommand")
        val secondsInput = intent?.getIntExtra("seconds_input", 0)
        when (intent?.action) {
            "START BACKGROUND" -> startCountDownTimer(secondsInput!!.toLong())
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    private fun startCountDownTimer(secondsInput: Long) {
        countDownTimer = object : CountDownTimer(secondsInput * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                    val minutes = (millisUntilFinished / 1000) / 60
                    val seconds = (millisUntilFinished / 1000) % 60
                    val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
            }
        }.start()
    }

}

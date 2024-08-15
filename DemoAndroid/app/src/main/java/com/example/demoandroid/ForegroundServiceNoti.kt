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
import android.widget.Button
import android.widget.RemoteViews
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.example.demoandroid.activities.ForegroundServiceActivity

class ForegroundServiceNoti : Service() {

    private val channelId = "foreground_service_channel"
    private var countDownTimer: CountDownTimer? = null


    override fun onCreate() {
        super.onCreate()
        Log.d("ForegroundService", "onCreate")
        createNotificationChannel()
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d("ForegroundService", "onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ForegroundService", "onStartCommand")
        val secondsInput = intent?.getIntExtra("seconds_input", 0)
        when (intent?.action) {
            "START" -> startCountDownTimer(secondsInput!!.toLong())
            "STOP" -> stopCountDownTimer()
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
                    updateNotification(timeLeftFormatted)
            }

            override fun onFinish() {
                updateNotification("Time out")
                stopCountDownTimer()
            }
        }.start()
    }

    private fun stopCountDownTimer() {
        countDownTimer?.cancel()
        stopForeground(Service.STOP_FOREGROUND_REMOVE)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(1)
        stopSelf()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }


    private fun updateNotification(timeLeft: String) {
        val notificationIntent = Intent(this, ForegroundServiceActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Foreground Service - Count Down")
            .setContentText("Time left: $timeLeft")
            .setSmallIcon(R.drawable.noti_ic)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}

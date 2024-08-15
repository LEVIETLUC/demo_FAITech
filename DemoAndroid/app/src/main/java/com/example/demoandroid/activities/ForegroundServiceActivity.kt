package com.example.demoandroid.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.demoandroid.ForegroundServiceNoti
import com.example.demoandroid.R

class ForegroundServiceActivity : AppCompatActivity() {

    private lateinit var buttonStop: Button
    private lateinit var buttonStart: Button
    private lateinit var secondsInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreground_service)

        buttonStop = findViewById(R.id.button_stop)
        buttonStart = findViewById(R.id.button_start)
        secondsInput = findViewById(R.id.edit_seconds)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
        }

        buttonStart.setOnClickListener {
            clickStartService()
        }
        buttonStop.setOnClickListener {
            clickStopService()
        }
    }

    private fun clickStartService() {
        val intent = Intent(this, ForegroundServiceNoti::class.java)
        intent.putExtra("seconds_input", secondsInput.text.toString().toInt())
        intent.action = "START"
        startService(intent)
    }

    private fun clickStopService() {
        val intent = Intent(this, ForegroundServiceNoti::class.java)
        intent.action = "STOP"
        startService(intent)
    }

}
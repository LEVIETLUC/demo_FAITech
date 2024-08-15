package com.example.demoandroid.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.demoandroid.BackgroundService
import com.example.demoandroid.ForegroundService
import com.example.demoandroid.R

class BackgroundServiceActivity : AppCompatActivity() {

    private lateinit var buttonStart: Button
    private lateinit var secondsInput: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_service)
        buttonStart = findViewById(R.id.button_start)
        secondsInput = findViewById(R.id.edit_seconds)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
        }

        buttonStart.setOnClickListener {
            clickStartService()
        }
    }

    private fun clickStartService() {
        val intent = Intent(this, BackgroundService::class.java)
        intent.putExtra("seconds_input", secondsInput.text.toString().toInt())
        intent.action = "START BACKGROUND"
        startService(intent)
    }
}
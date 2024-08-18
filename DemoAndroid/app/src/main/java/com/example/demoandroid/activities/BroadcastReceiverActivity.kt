package com.example.demoandroid.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.demoandroid.BroadcastReceiverCus
import com.example.demoandroid.R

class BroadcastReceiverActivity : AppCompatActivity() {
    private lateinit var broadcastReceiverCus: BroadcastReceiverCus
    private lateinit var tvWifiStatus : TextView
    private lateinit var tvAirplaneModeStatus : TextView
    private lateinit var tvBatteryValue : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_receiver)
        tvWifiStatus = findViewById(R.id.text_view_wifi)
        tvAirplaneModeStatus = findViewById(R.id.text_view_airplane)
        tvBatteryValue = findViewById(R.id.text_view_battery)
        broadcastReceiverCus = BroadcastReceiverCus(tvWifiStatus, tvAirplaneModeStatus, tvBatteryValue)
    }

    override fun onStart() {
        super.onStart()
        Log.d("BroadcastReceiverActivity", "onStart")
        val intentFilter = IntentFilter().apply {
            addAction("android.net.conn.CONNECTIVITY_CHANGE")
            addAction("android.intent.action.AIRPLANE_MODE")
            addAction("android.intent.action.BATTERY_CHANGED")
        }
        registerReceiver(broadcastReceiverCus, intentFilter)

    }

    override fun onStop() {
        super.onStop()
        Log.d("BroadcastReceiverActivity", "onStop")
        unregisterReceiver(broadcastReceiverCus)
    }
}
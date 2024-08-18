package com.example.demoandroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.provider.Settings
import android.util.Log
import android.widget.TextView

class BroadcastReceiverCus(
    private val tvWifiStatus: TextView,private val tvAirplaneModeStatus: TextView, private val tvBatteryValue: TextView) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ConnectivityManager.CONNECTIVITY_ACTION -> handleConnectivityChange(context, intent)
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> handleAirplaneModeChange(context, intent)
            Intent.ACTION_BATTERY_CHANGED -> handleBatteryChange(context, intent)

        }
    }

    private fun handleConnectivityChange(context: Context?, intent: Intent?) {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        val isConnected = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (isConnected) {
            Log.d("BroadcastReceiverCus", "Internet connection")
            tvWifiStatus.text = "Connected"
        } else {
            Log.d("BroadcastReceiverCus", "No internet connection")
            tvWifiStatus.text = "Disconnected"
        }
    }
    private fun handleAirplaneModeChange(context: Context?, intent: Intent?) {
        val isAirplaneModeOn = Settings.Global.getInt(
            context?.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0

        val status = if (isAirplaneModeOn) "On" else "Off"
        tvAirplaneModeStatus.text = status
        Log.d("BroadcastReceiverCus", "Airplane mode: $status")
    }

    private fun handleBatteryChange(context: Context?, intent: Intent?) {
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val batteryPct = level * 100 / scale.toFloat()

        tvBatteryValue.text = "${batteryPct.toInt()}%"
        Log.d("BroadcastReceiverCus", "Battery level: $batteryPct%")
    }
}
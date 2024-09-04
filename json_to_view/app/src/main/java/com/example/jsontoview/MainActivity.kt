package com.example.jsontoview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jsonObject = FileUltis.readFile(this@MainActivity, "sampleUi.json")
                val rootViewData = PraseJson.parseJsonToViewData(jsonObject)
                withContext(Dispatchers.Main) {
                    Log.d("JsonToViewActivity", "jsonObject: $jsonObject")
                    Log.d("JsonToViewActivity", "rootViewData: $rootViewData")
                    val customDrawUI = CustomDrawUI(this@MainActivity)
                    customDrawUI.setRootView(rootViewData)
                    setContentView(customDrawUI)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("JsonToViewActivity", "Error reading JSON file", e)
            }
        }
    }
}
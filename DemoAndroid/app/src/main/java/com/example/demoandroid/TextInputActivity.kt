package com.example.demoandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class TextInputActivity : AppCompatActivity() {

    private lateinit var buttonBack : Button
    private lateinit var editTextData : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_input)
        val dataText = intent.getStringExtra("data text")
        findViewById<EditText>(R.id.edit_data_text).setHint(dataText)
        buttonBack = findViewById(R.id.button_back)
        editTextData = findViewById(R.id.edit_data_text)
        buttonBack.setOnClickListener {
            val textData = editTextData.text.toString()
            Intent(this, TextViewActivity::class.java).also {
                it.putExtra("data text response", textData)
                startActivity(it)
            }
        }

    }
}
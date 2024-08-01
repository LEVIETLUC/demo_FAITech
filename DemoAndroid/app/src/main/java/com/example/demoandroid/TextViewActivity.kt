package com.example.demoandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TextViewActivity : AppCompatActivity() {

    private lateinit var buttonNext : Button
    private lateinit var textView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)
        buttonNext = findViewById(R.id.button_next)
        textView  = findViewById(R.id.text_view_data)
        inputText("Hello")
        val dataText = intent.getStringExtra("data text response")
        if(dataText != null) {
            inputText(dataText)
        }
        else
        {
            inputText("Hello")
        }
        buttonNext.setOnClickListener {
            val textData = textView.text.toString()
            Intent(this, TextInputActivity::class.java).also {
                it.putExtra("data text", textData)
                startActivity(it)
            }
        }
    }

    fun inputText(putName : String) {
        textView.text = putName
    }
}
package com.example.demoandroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.demoandroid.R

class HelloTextViewActivity : AppCompatActivity() {

    private lateinit var textViewName : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_text_view)

        textViewName = findViewById(R.id.text_view_name)
        inputName("Hi, my name is Luc")
    }

    private fun inputName(putName : String) {
        textViewName.setText(putName)
    }
}
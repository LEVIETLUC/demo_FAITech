package com.example.demoandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textViewName : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewName = findViewById(R.id.text_view_name)
        inputName("Hi, my name is Luc")
    }

    private fun inputName(putName : String) {
        textViewName.setText(putName)
    }
}
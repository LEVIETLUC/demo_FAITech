package com.example.demoandroid

import android.app.Activity
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
        init()
        val dataText = intent.getStringExtra("data text")
        editTextData.hint = dataText
        buttonBack.setOnClickListener {
            val textData = editTextData.text.toString()
            senData(textData)
            finish()
        }

    }

    private fun init(){
        buttonBack = findViewById(R.id.button_back)
        editTextData = findViewById(R.id.edit_data_text)
    }



    private fun senData(textData: String){
        val resultIntent = Intent()
        resultIntent.putExtra("data text response", textData)
        setResult(Activity.RESULT_OK, resultIntent)
    }
}
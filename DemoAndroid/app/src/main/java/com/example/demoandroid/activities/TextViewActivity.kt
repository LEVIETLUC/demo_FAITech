package com.example.demoandroid.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.demoandroid.R

class TextViewActivity : AppCompatActivity() {

    private lateinit var buttonNext : Button
    private lateinit var textView : TextView
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)
        init()
        inputText("Hello")
        reciveData()

        buttonNext.setOnClickListener {
            senData(textView.text.toString())
        }
    }

    private fun init(){
        buttonNext = findViewById(R.id.button_next)
        textView  = findViewById(R.id.text_view_data)
    }

    private fun senData(textData : String){
        val intent = Intent(this, TextInputActivity::class.java)
        intent.putExtra("data text", textData)
        resultLauncher.launch(intent)
    }

    private fun reciveData(){
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val textData = data?.getStringExtra("data text response")
                inputText(textData.toString())
            }
            else
            {
                inputText("Hello")
            }
        }    }

    private fun inputText(putName : String) {
        textView.text = putName
    }
}
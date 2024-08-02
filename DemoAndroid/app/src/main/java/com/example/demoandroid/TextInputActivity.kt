package com.example.demoandroid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        isEmptyEditText(editTextData)
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

    private fun isEmptyEditText(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Không cần thực hiện gì ở đây
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Kiểm tra xem EditText có dữ liệu không
                buttonBack.isEnabled = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                // Không cần thực hiện gì ở đây
            }
        })
    }
}
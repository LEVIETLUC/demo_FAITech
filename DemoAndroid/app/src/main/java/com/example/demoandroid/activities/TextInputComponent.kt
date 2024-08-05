package com.example.demoandroid.activities

import android.R
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class TextInputComponent : AppCompatActivity() {


    private lateinit var buttonBack : Button
    private lateinit var editTextData : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linearLayout = LinearLayout(this).apply{
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        editTextData = EditText(this).apply {
            hint = "Hello"
            layoutParams = LinearLayout.LayoutParams(
                convertDpToPx(380),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            background = customizeEditText()
            val padding = convertDpToPx(10) // Padding in dp
            setPadding(padding, padding, padding, padding)
        }

        buttonBack = Button(this).apply{
            text = "Get text and back"
            setTextColor(ContextCompat.getColor(this@TextInputComponent, R.color.white))
            isAllCaps = false
            layoutParams = LinearLayout.LayoutParams(
                convertDpToPx(200),
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = convertDpToPx(15)
            }
            background = customButton()
            if(editTextData.text.isNullOrEmpty()){
                isEnabled = false
            }
            setOnClickListener {

                senData(editTextData.text.toString())
            }
        }

        linearLayout.addView(editTextData)
        linearLayout.addView(buttonBack)
        setContentView(linearLayout)


    }

    private fun customButton() : GradientDrawable {
        val drawable = GradientDrawable().apply{
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 50f
            setColor(ContextCompat.getColor(this@TextInputComponent, R.color.holo_purple))
        }
        return drawable
    }

    private fun convertDpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun customizeEditText() : GradientDrawable {
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(ContextCompat.getColor(this@TextInputComponent, android.R.color.white)) // Background color
            setStroke(2, ContextCompat.getColor(this@TextInputComponent, android.R.color.black)) // Border width and color
            cornerRadius = 10f // Rounded corners
        }
        return drawable


    }


    private fun senData(textData: String){
        val resultIntent = Intent()
        resultIntent.putExtra("data text response", textData)
        setResult(Activity.RESULT_OK, resultIntent)
    }

    private fun isEmptyEditText(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buttonBack.isEnabled = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}
package com.example.demoandroid.activities

import android.R
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor

class TextViewComponent : AppCompatActivity() {


    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var textView: TextView
    private lateinit var buttonNext: Button

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

        textView = TextView(this).apply {
            text = "Hello"
            textSize = 22f
            gravity = Gravity.CENTER
            setTypeface(null, Typeface.BOLD_ITALIC)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        reciveData()

        buttonNext = Button(this).apply{
            text = "Next"
             setTextColor(getColor(this@TextViewComponent, R.color.white))
            isAllCaps = false
            layoutParams = LinearLayout.LayoutParams(
                convertDpToPx(200),
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = convertDpToPx(15)
            }
            background = customButton()
            setOnClickListener {
                senData(textView.text.toString())
            }
        }

        linearLayout.addView(textView)
        linearLayout.addView(buttonNext)
        setContentView(linearLayout)
    }

    private fun customButton() : GradientDrawable {
        val drawable = GradientDrawable().apply{
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 50f
            setColor(getColor(this@TextViewComponent, R.color.holo_purple))
        }
        return drawable
    }

    private fun convertDpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
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
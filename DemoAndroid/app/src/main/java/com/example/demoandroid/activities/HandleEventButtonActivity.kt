package com.example.demoandroid.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demoandroid.R

class HandleEventButtonActivity : AppCompatActivity() {

    private lateinit var linearLayout: LinearLayout
    private lateinit var buttonNext: Button
    private lateinit var textView : TextView

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handle_event_button)
        buttonNext = findViewById(R.id.button_next)
        textView  = findViewById(R.id.text_view_data)
        linearLayout = findViewById<CustomLinearLayout>(R.id.btn_layout)

        linearLayout.setOnClickListener {
            textView.text = "LinearLayout clicked"
        }

        buttonNext.setOnClickListener {
            textView.text = "Button clicked"
        }

    }
}

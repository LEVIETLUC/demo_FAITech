package com.example.demoandroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.demoandroid.R

class ScrollViewActivity : AppCompatActivity() {
    var scrollView: ScrollView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view)
        scrollView = findViewById(R.id.scroll_view_parent)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)


    }
}
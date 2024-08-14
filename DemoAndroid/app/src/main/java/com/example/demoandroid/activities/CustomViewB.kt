package com.example.demoandroid.activities

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class CustomViewB @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("CustomViewB", "dispatchTouchEventB: ACTION_DOWN")
            }
            MotionEvent.ACTION_UP -> {
                Log.d("CustomViewB", "dispatchTouchEventB: ACTION_UP")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("CustomViewB", "dispatchTouchEventB: ACTION_MOVE")
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d("CustomViewB", "dispatchTouchEventB: ACTION_CANCEL")
            }
        }
        return super.dispatchTouchEvent(ev)
    }

        override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("CustomViewB", "onTouchEventB: ${event?.action}")
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
                setBackgroundColor(0xFF1459d9.toInt())
            }

            MotionEvent.ACTION_UP -> {
                performClick()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        Log.d("CustomViewB", "performClick")
        return super.performClick()
    }
}

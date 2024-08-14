package com.example.demoandroid.activities

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

class CustomViewGroupA @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var disallowIntercept = false

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("CustomViewGroupA", "dispatchTouchEventA: ACTION_DOWN")
            }
            MotionEvent.ACTION_UP -> {
                Log.d("CustomViewGroupA", "dispatchTouchEventA: ACTION_UP")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("CustomViewGroupA", "dispatchTouchEventA: ACTION_MOVE")
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d("CustomViewGroupA", "dispatchTouchEventA: ACTION_CANCEL")
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("CustomViewGroupA", "onInterceptTouchEventA: ${ev?.action}")

        Log.d("CustomViewGroupA", "disallowIntercept: ${disallowIntercept}")
        if (disallowIntercept) {
            return false
        }

        if (ev?.action == MotionEvent.ACTION_DOWN) {
            return true
        }

        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("CustomViewGroupA", "onTouchEventA: ${event?.action}")
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                setBackgroundColor(0xFF8BC34A.toInt())
            }
            MotionEvent.ACTION_UP -> {
                performClick()
            }
        }
        return true
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        Log.d("CustomViewGroupA", "requestDisallowInterceptTouchEvent: $disallowIntercept")
        this.disallowIntercept = disallowIntercept
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    override fun performClick(): Boolean {
        Log.d("CustomViewGroupA", "performClick")
        return super.performClick()
    }
}

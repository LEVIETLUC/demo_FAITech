package com.example.demoandroid

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

class CustomScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ScrollView(context, attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let {
            println("Status: ${canScrollVertically(1)}")
            if(canScrollVertically(1) || canScrollVertically(-1)){
                parent.requestDisallowInterceptTouchEvent(true)
            }
            else
            {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if(!canScrollVertically(1) || !canScrollVertically(-1)){
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }
}
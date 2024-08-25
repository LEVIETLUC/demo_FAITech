package com.example.demoandroid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.BoringLayout
import android.text.Layout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.Gravity
import android.view.View

class CustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var text: String = "Default Text"
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 50f
    }
    private var boringLayout: BoringLayout? = null

    var textSize: Float
        get() = textPaint.textSize
        set(value) {
            textPaint.textSize = value
            requestLayout()
            invalidate()
        }
    var layoutGravity: Int = Gravity.NO_GRAVITY


    fun setText(text: String) {
        this.text = text
        requestLayout()
        invalidate()
    }

    fun setTextColor(color: Int) {
        textPaint.color = color
        invalidate()
    }

    fun setFontStyle(style: Int) {
        textPaint.typeface = Typeface.defaultFromStyle(style)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val metrics = BoringLayout.isBoring(text, textPaint)
        if (metrics != null) {
            val width = if (widthMode == MeasureSpec.EXACTLY) {
                widthSize
            } else {
                Math.min(metrics.width, widthSize)
            }
            val fontMetrics = textPaint.fontMetrics
            val height = (fontMetrics.bottom - fontMetrics.top).toInt()

            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val metrics = BoringLayout.isBoring(text, textPaint)
        if (metrics != null) {
            if (boringLayout == null || boringLayout?.text != text) {
                boringLayout = BoringLayout.make(
                    text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, metrics, false
                )
            }

            boringLayout?.draw(canvas)
        } else {
            canvas.drawText(text, 10f, measuredHeight / 2f, textPaint)
        }
    }
}
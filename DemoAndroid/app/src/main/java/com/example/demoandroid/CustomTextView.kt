package com.example.demoandroid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.BoringLayout
import android.text.Layout
import android.text.TextPaint
import android.util.AttributeSet
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
            requestLayout()  // Text size changed, we might need to re-measure
            invalidate()     // Redraw the view with the new text size
        }

    fun setText(text: String) {
        this.text = text
        requestLayout()  // Text changed, we might need to re-measure
        invalidate()     // Redraw the view with the new text
    }

    fun setTextColor(color: Int) {
        textPaint.color = color
        invalidate()  // Redraw the view with the new text color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        // Calculate width based on BoringLayout metrics
        val metrics = BoringLayout.isBoring(text, textPaint)
        if (metrics != null) {
            val width = if (widthMode == MeasureSpec.EXACTLY) {
                widthSize
            } else {
                Math.min(metrics.width, widthSize)
            }

            // Calculate height based on text paint font metrics
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
            // Create BoringLayout if it hasn't been created or if the text has changed
            if (boringLayout == null || boringLayout?.text != text) {
                boringLayout = BoringLayout.make(
                    text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, metrics, false
                )
            }

            boringLayout?.draw(canvas)
        } else {
            // Fallback to drawing with Canvas if BoringLayout is not used
            // Use measuredHeight instead of height
            canvas?.drawText(text, 10f, measuredHeight / 2f, textPaint)
        }
    }
}
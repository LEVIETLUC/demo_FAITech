package com.example.demoandroid.activities

import android.content.Context
import android.graphics.*
import android.text.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.demoandroid.R

class CustomView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, android.R.color.black)
        textSize = 80f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
    }

    private val buttonPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#6454a4")
    }

    private val buttonTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, android.R.color.white)
        textSize = 50f
        textAlign = Paint.Align.CENTER
    }

    private val imageBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.demo_img)

    private val buttonRect = android.graphics.RectF()
    private var boringLayout: BoringLayout? = null

    private var buttonText: String = "Next"
    private var text: String = "Hello"
    private var textX: Float = 0f
    private var textY: Float = 0f
    private val cornerRadius: Float = 60f
    private var valButtonTextY: Float = 0f
    private var valImageLeft: Float = 0f
    private var valImageTop: Float = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initBoringLayout(text, textPaint, w)
        calculateTextPosition(w, h)
        calculateButtonAndImagePositions(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        boringLayout?.let {
            canvas.save()
            canvas.translate(textX, textY)
            it.draw(canvas)
            canvas.restore()
        }

        canvas.drawRoundRect(buttonRect, cornerRadius, cornerRadius , buttonPaint)

        val buttonTextX = buttonRect.centerX()
        val buttonTextY = valButtonTextY
        canvas.drawText(buttonText, buttonTextX, buttonTextY, buttonTextPaint)

        val imageLeft = valImageLeft
        val imageTop = valImageTop
        canvas.drawBitmap(imageBitmap, imageLeft, imageTop, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (buttonRect.contains(event.x, event.y)) {
                if(buttonPaint.color == Color.parseColor("#6454a4")) {
                    buttonPaint.color = Color.parseColor("#ff0000")
                } else {
                    buttonPaint.color = Color.parseColor("#6454a4")
                }
                invalidate()
                performClick()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun initBoringLayout(text: String, textPaint: TextPaint, width: Int) {
        val metrics = BoringLayout.isBoring(text, textPaint)
        if (metrics != null) {
            boringLayout = BoringLayout.make(
                text,
                textPaint,
                width,
                Layout.Alignment.ALIGN_CENTER,
                1.0f,
                0.0f,
                metrics,
                false

            )
        }
    }

    private fun calculateTextPosition(width: Int, height: Int) {
        val textWidth = boringLayout?.width ?: 0
        val textHeight = boringLayout?.height ?: 0

        textX = (width - textWidth) / 2f
        textY = height / 3f
    }

    private fun calculateButtonAndImagePositions(width: Int, height: Int) {
        val buttonWidth = 650
        val buttonHeight = 130
        val buttonLeft = (width - buttonWidth) / 2f
        val buttonTop = textY + (boringLayout?.height ?: 0) + 50
        buttonRect.set(buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight)
        valButtonTextY = buttonRect.top + buttonRect.height() / 2f + buttonTextPaint.textSize / 4
        valImageLeft = (width - imageBitmap.width) / 2f
        valImageTop = buttonRect.bottom + 50
    }

}

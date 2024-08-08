package com.example.demoandroid.activities

import android.content.Context
import android.graphics.*
import android.text.*
import android.util.AttributeSet
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
    private lateinit var boringLayout: BoringLayout

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = 600
        val desiredHeight = 800

        val width = resolveSize(desiredWidth, widthMeasureSpec)
        val height = resolveSize(desiredHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val canvasWidth = this.width
        val canvasHeight = this.height

        val text = "Hello"
        val metrics = BoringLayout.isBoring(text, textPaint)

        if (metrics != null) {
            boringLayout = BoringLayout.make(
                text,
                textPaint,
                canvasWidth,
                Layout.Alignment.ALIGN_CENTER,
                1.0f,
                0.0f,
                metrics,
                false
            )
        }

        val textWidth = boringLayout.width
        val textHeight = boringLayout.height

        val textX = (canvasWidth - textWidth) / 2f
        val textY = canvasHeight / 2.5f

        boringLayout.let {
            canvas.save()
            canvas.translate(textX, textY)
            it.draw(canvas)
            canvas.restore()
        }

        val buttonWidth = 650
        val buttonHeight = 130
        val buttonLeft = (canvasWidth - buttonWidth) / 2f
        val buttonTop = textY + textHeight + 50
        val cornerRadius = 60f
        buttonRect.set(buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight)
        canvas.drawRoundRect(buttonRect, cornerRadius, cornerRadius, buttonPaint)

        val buttonText = "Next"
        val buttonTextX = buttonRect.centerX()
        val buttonTextY = buttonTop + buttonHeight / 2f + buttonTextPaint.textSize / 4
        canvas.drawText(buttonText, buttonTextX, buttonTextY, buttonTextPaint)

        val imageLeft = (canvasWidth - imageBitmap.width) / 2f
        val imageTop = buttonTop + buttonHeight + 50
        canvas.drawBitmap(imageBitmap, imageLeft, imageTop, null)
    }
}

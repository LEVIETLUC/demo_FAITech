package com.example.demoandroid

import android.content.Context
import android.graphics.*
import android.text.BoringLayout
import android.text.Layout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

class CustomTextView(context: Context,text: String, attrs: AttributeSet? = null) : View(context, attrs) {

    private val textPaint = TextPaint().apply {
        color = Color.BLACK
        textSize = 100f
        typeface = Typeface.DEFAULT_BOLD
    }

    private lateinit var boringLayout: BoringLayout
    private var text = "Default Text"
    private var gravity = Gravity.START
    private var layoutGravity = Gravity.CENTER

    private var layoutWidth = -2
    private var layoutHeight = -2

    private var canvasWidth = 0
    private var canvasHeight = 0
    private var textHeight = 0
    private var textWidth = 0
    private var textX = 0
    private var textY = 0

    private var backgroundColor = Color.TRANSPARENT // Biến lưu trữ màu nền
    private val backgroundPaint = Paint() // Paint để vẽ nền

    init {
        // Khởi tạo màu nền với giá trị mặc định
        backgroundPaint.color = Color.RED
        backgroundPaint.style = Paint.Style.FILL
        this.text=text
        invalidate()
    }


    fun setTextColor(color: Int) {
        textPaint.color = color
        invalidate()
    }

    fun setBackGroundColor(color: Int) {
        backgroundColor = color
        backgroundPaint.color = color
    }

    fun setTextSize(size: Float) {
        textPaint.textSize = size
        invalidate()
    }

    fun setTextStyle(style: Int) {
        textPaint.typeface = Typeface.defaultFromStyle(style)
        invalidate()
    }

    fun setGravity(gravity: Int) {
        this.gravity = gravity
        invalidate()
    }

    fun setLayoutGravity(layoutGravity: Int) {
        this.layoutGravity = layoutGravity
        invalidate()
    }

    fun setLayoutWidth(layoutWidth: Int) {
        this.layoutWidth = layoutWidth
        invalidate()
    }

    fun setLayoutHeight(layoutHeight: Int) {
        this.layoutHeight = layoutHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Calculate width based on layoutWidth
        canvasWidth = when (layoutWidth) {
            WindowManager.LayoutParams.MATCH_PARENT -> if (parent is ViewGroup) (parent as ViewGroup).measuredWidth else MeasureSpec.getSize(
                widthMeasureSpec
            )

            WindowManager.LayoutParams.WRAP_CONTENT -> textWidth
            else -> layoutWidth
        }

        // Calculate height based on layoutHeight
        canvasHeight = when (layoutHeight) {
            WindowManager.LayoutParams.MATCH_PARENT -> if (parent is ViewGroup) (parent as ViewGroup).measuredHeight else MeasureSpec.getSize(
                heightMeasureSpec
            )

            WindowManager.LayoutParams.WRAP_CONTENT -> textHeight
            else -> layoutHeight
        }
        updateTextSize()
        requestLayout()

        setMeasuredDimension(canvasWidth, canvasHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateTextSize()
        invalidate()
        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        Log.e("CustomTextView", "xin loi")
        super.onDraw(canvas)
        canvas.drawRect(
            (this.width - canvasWidth).toFloat(),
            (this.height - canvasHeight).toFloat(),
            canvasWidth.toFloat(),
            canvasHeight.toFloat(),
            backgroundPaint
        )

        boringLayout.let {
            canvas.save()
            canvas.translate(textX.toFloat(), textY.toFloat())
            it.draw(canvas)
            canvas.restore()
        }

//        super.onDraw(canvas)

    }

    private fun updateTextSize() {
        val metrics = BoringLayout.isBoring(text, textPaint)

        if (metrics != null) {
            val desiredWidth = textPaint.measureText(text).toInt()
            boringLayout = BoringLayout.make(
                text,
                textPaint,
                desiredWidth,
                Layout.Alignment.ALIGN_NORMAL,
                1.0f,
                0.0f,
                metrics,
                false
            )
            textHeight = boringLayout.height
            textWidth = boringLayout.width

            if (layoutWidth == WindowManager.LayoutParams.WRAP_CONTENT) {
                canvasWidth = textWidth
            }
            if (layoutHeight == WindowManager.LayoutParams.WRAP_CONTENT) {
                canvasHeight = textHeight
            }

//            val parent = parent as? ViewGroup
//            val parentWidth = parent?.measuredWidth ?: 0
//            val parentHeight = parent?.measuredHeight ?: 0
//
////            textX = when (layoutGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
////                Gravity.LEFT -> 0
////                Gravity.CENTER_HORIZONTAL -> (parentWidth - canvasWidth) / 2
////                Gravity.RIGHT -> parentWidth - canvasWidth
////                Gravity.CENTER -> (parentWidth - canvasWidth) / 2
////                else -> 0
////            }
////
////            textY = when (layoutGravity and Gravity.VERTICAL_GRAVITY_MASK) {
////                Gravity.TOP -> 0
////                Gravity.CENTER_VERTICAL -> (parentHeight - canvasHeight) / 2
////                Gravity.BOTTOM -> parentHeight - canvasHeight
////                Gravity.CENTER -> (parentHeight - canvasHeight) / 2
////                else -> 0
////            }

            textX += when (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                Gravity.LEFT -> 0
                Gravity.CENTER_HORIZONTAL -> (canvasWidth - textWidth) / 2
                Gravity.RIGHT -> canvasWidth - textWidth
                Gravity.CENTER -> (canvasWidth - textWidth) / 2
                else -> 0
            }


            textY += when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
                Gravity.TOP -> 0
                Gravity.CENTER_VERTICAL -> (canvasHeight - textHeight) / 2
                Gravity.BOTTOM -> canvasHeight - textHeight
                Gravity.CENTER -> (canvasHeight - textHeight) / 2
                else -> 0
            }
            Log.e("CustomTextView", "textX: $textX, textY: $textY")
        }
    }
}
package com.example.demoandroid

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.graphics.BitmapFactory
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class CustomImageView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val bitmapPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    private var bitmap: Bitmap? = null
    private var bitmapWidth = 0
    private var bitmapHeight = 0

    private var layoutGravity = Gravity.CENTER
    private var gravity = Gravity.CENTER

    private var layoutWidth = -1
    private var layoutHeight = -2

    private var canvasWidth = 0
    private var canvasHeight = 0
    private var bitmapX = 0
    private var bitmapY = 0

    fun setBitmapFromResource(resId: Int) {
        val originalBitmap = BitmapFactory.decodeResource(context.resources, resId)
        originalBitmap?.let {
            val desiredHeight = 600
            val scaleFactor = desiredHeight.toFloat() / it.height
            val desiredWidth = (it.width * scaleFactor).toInt()
            bitmap = Bitmap.createScaledBitmap(it, desiredWidth, desiredHeight, true)

            bitmap?.let { scaledBitmap ->
                bitmapWidth = scaledBitmap.width
                bitmapHeight = scaledBitmap.height
            }
        }
        invalidate()
    }

    fun setBitmapFromPath(filePath: String) {
        bitmap = BitmapFactory.decodeFile(filePath)
        bitmap?.let {
            bitmapWidth = it.width
            bitmapHeight = it.height
        }
    }

    fun setLayoutGravity(layoutGravity: Int) {
        this.layoutGravity = layoutGravity
        invalidate()
    }

    fun setGravity(gravity: Int) {
        this.gravity = gravity
        invalidate()
    }

    fun setLayoutWidth(layoutWidth: Int) {
        this.layoutWidth = layoutWidth
        invalidate()
    }

    fun setLayoutHeight(layoutHeight: Int) {
        this.layoutHeight = layoutHeight
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        canvasWidth = when (layoutWidth) {
            WindowManager.LayoutParams.MATCH_PARENT -> if (parent is ViewGroup) (parent as ViewGroup).width else MeasureSpec.getSize(widthMeasureSpec)
            WindowManager.LayoutParams.WRAP_CONTENT -> bitmapWidth
            else -> layoutWidth
        }

        canvasHeight = when (layoutHeight) {
            WindowManager.LayoutParams.MATCH_PARENT -> if (parent is ViewGroup) (parent as ViewGroup).height else MeasureSpec.getSize(heightMeasureSpec)
            WindowManager.LayoutParams.WRAP_CONTENT -> bitmapHeight
            else -> layoutHeight
        }

        val flag = 10000
        for(i in 0..flag){
            Log.e("CustomTextView", "onMeasure")
        }

        setMeasuredDimension(canvasWidth, canvasHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val flag = 10000
        for(i in 0..flag){
            Log.e("CustomTextView", "onMeasure")
        }
        updateCanvasSize()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val flag = 10000
        for(i in 0..flag){
            Log.e("CustomTextView", "onMeasure")
        }
            bitmap?.let {
            canvas.save()
            canvas.drawBitmap(it, bitmapX.toFloat(), bitmapY.toFloat(), bitmapPaint)
            canvas.restore()
        }
    }

    private fun updateCanvasSize() {
        val parent = parent as? ViewGroup
        if (parent is RecyclerView) {
            return
        }

        bitmapX += when (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
            Gravity.LEFT -> 0
            Gravity.CENTER_HORIZONTAL -> (canvasWidth - bitmapWidth) / 2
            Gravity.RIGHT -> canvasWidth - bitmapWidth
            Gravity.CENTER -> (canvasWidth - bitmapWidth) / 2
            else -> 0
        }

        bitmapY += when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
            Gravity.TOP -> 0
            Gravity.CENTER_VERTICAL -> (canvasHeight - bitmapHeight) / 2
            Gravity.BOTTOM -> canvasHeight - bitmapHeight
            Gravity.CENTER -> (canvasHeight - bitmapHeight) / 2
            else -> 0
        }
    }
}
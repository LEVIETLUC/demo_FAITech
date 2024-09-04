package com.example.jsontoview

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.BoringLayout
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.ceil

class CustomDrawUI(context: Context) : View(context) {

    private var rootView: FView? = null
    private var statusBarHeight: Int = 0

    init {
        setOnApplyWindowInsetsListener { _, insets ->
            statusBarHeight = insets.systemWindowInsetTop
            insets
        }
    }

    fun setRootView(fView: FView) {
        this.rootView = fView
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        Log.e("onMeasure", "Measure view")
        rootView?.let {
            measureView(it, width, height)
        }
        setMeasuredDimension(width, height)
    }

    private fun measureView(fView: FView, parentWidth: Int, parentHeight: Int) {
        val widthDimension = fView.props?.width ?: FView.Dimension(-1, UnitConfig.PX)
        val heightDimension = fView.props?.height ?: FView.Dimension(-1, UnitConfig.PX)

        val width = convertToPixels(widthDimension)
        val height = convertToPixels(heightDimension)

        // Calculate measured width and height based on width and height settings
        fView.measuredWidth = when (width) {
            ViewGroup.LayoutParams.MATCH_PARENT -> parentWidth
            ViewGroup.LayoutParams.WRAP_CONTENT -> measureContentWidth(fView)
            else -> width
        }

        fView.measuredHeight = when (height) {
            ViewGroup.LayoutParams.MATCH_PARENT -> parentHeight
            ViewGroup.LayoutParams.WRAP_CONTENT -> measureContentHeight(fView)
            else -> height
        }

        fView.props?.drawable?.let { drawable ->
            Log.e("MeasureView", "Measure Drawable Data: ${drawable.data}, Measured Width: ${fView.measuredWidth}, Measured Height: ${fView.measuredHeight}")

        }

        // Measure child views recursively
        fView.children?.forEach { child ->
            measureView(child, fView.measuredWidth, fView.measuredHeight)
        }
    }

    private fun measureContentWidth(fView: FView): Int {
        if (fView.props?.drawable?.type == TypeConfig.TEXT_VIEW) {
            val textPaint = TextPaint()
            textPaint.textSize = fView.props!!.drawable?.props?.textSize ?: 16f
            val text = fView.props!!.drawable?.data
            val metrics = BoringLayout.isBoring(text, textPaint)
            val width = BoringLayout.getDesiredWidth(text, textPaint)
            return width.toInt()
        }
        return 0
    }

    private fun measureContentHeight(fView: FView): Int {
        if (fView.props?.drawable?.type == TypeConfig.TEXT_VIEW) {
            val textPaint = TextPaint().apply {
                textSize = fView.props!!.drawable?.props?.textSize ?: 16f
            }
            val text = fView.props!!.drawable?.data ?: ""

            // Kiểm tra xem văn bản có "boring" không
            val metrics = BoringLayout.isBoring(text, textPaint)

            return if (metrics != null) {
                // Tính toán chiều cao dựa trên chiều cao của một dòng
                val lineHeight = ceil(textPaint.fontMetrics.descent - textPaint.fontMetrics.ascent.toDouble()).toInt()
                lineHeight
            } else {
                // Nếu văn bản không phải là "boring", sử dụng StaticLayout
                val layout = StaticLayout(text, textPaint, measureContentWidth(fView), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false)
                layout.height
            }
        }
        return 0
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.e("onLayout", "Layout view")
        rootView?.let {
            layoutView(it, left, top + statusBarHeight, right, bottom)
        }
    }

    private fun layoutView(fView: FView, left: Int, top: Int, right: Int, bottom: Int) {
        val paddingLeft = fView.props?.padding?.left ?: 0
        val paddingTop = fView.props?.padding?.top ?: 0
        val childLeft = left + paddingLeft
        val childTop = top + paddingTop
        val width = fView.measuredWidth
        val height = fView.measuredHeight

        val gravity = fView.props?.gravity ?: GravityConfig.CENTER
        Log.e("LayoutView", "Layout gravity: $gravity")

        Log.e("LayoutView", "Layout Data: ${fView.props?.drawable?.data}, Position: ($childLeft, $childTop), Size: ($width, $height)")

        // Cập nhật layoutRect cho fView
        fView.layoutRect = Rect(childLeft, childTop, childLeft + width, childTop + height)

//        fView.layoutRect = Rect(childLeft, childTop, childLeft + width, childTop + height)
        fView.props?.drawable?.let { drawable ->
            Log.e("LayoutView", "Layout Drawable Data: ${drawable.data}, Position: (${fView.layoutRect.left}, ${fView.layoutRect.top}), Size: (${fView.layoutRect.width()}, ${fView.layoutRect.height()})")

        }
        fView.children?.let { children ->
            var offsetX = 0
            var offsetY = 0

            children.forEach { child ->
                val marginLeft = child.props?.margin?.left ?: 0
                val marginTop = child.props?.margin?.top ?: 0

                val xPos = when(gravity){
                    GravityConfig.CENTER -> (right - left - child.measuredWidth) / 2 + left
                    GravityConfig.RIGHT -> right - child.measuredWidth
                    GravityConfig.LEFT -> left
                    else -> left
                }

                val yPos = when(gravity){
                    GravityConfig.CENTER -> (bottom - top - child.measuredHeight) / 2 + top
                    GravityConfig.BOTTOM -> bottom - child.measuredHeight
                    GravityConfig.TOP -> top
                    else -> top
                }

//                val xPos = when (gravity) {
//                    GravityConfig.CENTER -> (width - child.measuredWidth) / 2 + childLeft
//                    GravityConfig.RIGHT -> childLeft + width - child.measuredWidth
//                    GravityConfig.LEFT -> childLeft
//                    else -> childLeft
//                }
//
//                val yPos = when (gravity) {
//                    GravityConfig.CENTER -> (height - child.measuredHeight) / 2 + childTop
//                    GravityConfig.BOTTOM -> childTop + height - child.measuredHeight
//                    GravityConfig.TOP -> childTop
//                    else -> childTop
//                }

                val childLeftPos = xPos + childLeft + marginLeft + offsetX
                val childTopPos = yPos + childTop + marginTop + offsetY

                layoutView(child, childLeftPos, childTopPos, childLeftPos + child.measuredWidth, childTopPos + child.measuredHeight)

                if (fView.props?.orientation == OrientationConfig.VERTICAL) { // Vertical orientation
                    offsetY += child.measuredHeight + (child.props?.margin?.bottom ?: 0) + marginTop
                } else { // Horizontal orientation
                    offsetX += child.measuredWidth + (child.props?.margin?.right ?: 0) + marginLeft
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.e("onDraw", "Draw view")
        rootView?.let {
            drawView(it, canvas)
        }
    }

    private fun drawView(fView: FView, canvas: Canvas) {
        val paint = Paint()
        fView.props?.background?.let {
            paint.color = Color.parseColor(it.color)
            canvas.drawRect(fView.layoutRect, paint)
        }

        fView.props?.drawable?.let { drawable ->
            when (drawable.type) {
                TypeConfig.TEXT_VIEW -> drawText(canvas, fView.props!!, fView.layoutRect)
                TypeConfig.IMAGE_VIEW -> drawImage(canvas, drawable, fView.layoutRect, fView)
            }
        }

        fView.children?.forEach { child ->
            canvas.save()
            drawView(child, canvas)
            canvas.restore()
        }
    }
    private fun drawText(canvas: Canvas, props: FView.ViewProps, layoutRect: Rect) {
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = props.drawable?.props?.textSize ?: 16f
            color = Color.parseColor(props.drawable?.props?.textColor ?: "#000000")
        }

        val text = props.drawable?.data

        // Measure the text width
        val width = BoringLayout.getDesiredWidth(text, textPaint).toInt()
        val metrics = BoringLayout.isBoring(text, textPaint)

        // Create a BoringLayout to draw the text
        val layout = BoringLayout.make(
            text,
            textPaint,
            width,
            Layout.Alignment.ALIGN_NORMAL, // Bạn có thể thay đổi ALIGN_NORMAL thành ALIGN_CENTER hoặc ALIGN_OPPOSITE
            1.0f, // Spacing Multiplier
            0.0f, // Spacing Add
            metrics, // Use BoringLayout.Metrics instead of FontMetricsInt
            true
        )

        // Tính toán vị trí của văn bản dựa trên căn chỉnh
        val xPos = when (props.gravity) {
            GravityConfig.CENTER -> layoutRect.left + (layoutRect.width() - width) / 2
            GravityConfig.RIGHT -> layoutRect.left + layoutRect.width() - width
            else -> layoutRect.left
        }
        val yPos = when (props.gravity) {
            GravityConfig.CENTER -> layoutRect.top + (layoutRect.height() - layout.height) / 2
            GravityConfig.BOTTOM -> layoutRect.top + layoutRect.height() - layout.height
            else -> layoutRect.top
        }

        // Draw text using BoringLayout
        canvas.save()
        canvas.translate(xPos.toFloat(), yPos.toFloat())
        layout.draw(canvas)
        canvas.restore()
    }
//    private fun drawImage(canvas: Canvas, drawable: FView.Drawable, layoutRect: Rect, fView: FView) {
//        val url = drawable.data
//        Thread {
//            try {
//                val connection = URL(url).openConnection() as HttpURLConnection
//                connection.doInput = true
//                connection.connect()
//                val inputStream = connection.inputStream
//                val bitmap = BitmapFactory.decodeStream(inputStream)
//                val scaledBitmap = Bitmap.createScaledBitmap(bitmap, layoutRect.width(), layoutRect.height(), false)
//                fView.bitmap = scaledBitmap
//
//                // Post the drawing operation to the main thread
//                post {
//                    canvas.drawBitmap(scaledBitmap, layoutRect.left.toFloat(), layoutRect.top.toFloat(), null)
//                    invalidate()
//                }
//            } catch (e: Exception) {
//                Log.e("ImageLoadError", "Failed to load image from $url", e)
//            }
//        }.start()
//    }

//    private fun drawImage(canvas: Canvas, drawable: FView.Drawable, layoutRect: Rect, fView: FView) {
//        GlideApp.with(context)
//            .asBitmap()
//            .load(drawable.data)
//            .placeholder(R.drawable.empty_img)
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    val bitmap = Bitmap.createScaledBitmap(resource, layoutRect.width(), layoutRect.height(), false)
//                    fView.bitmap = bitmap
//                    canvas.drawBitmap(bitmap, layoutRect.left.toFloat(), layoutRect.top.toFloat(), null)
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    // Handle cleanup if necessary
//                }
//
//                override fun onLoadFailed(errorDrawable: Drawable?) {
//                    super.onLoadFailed(errorDrawable)
//                    Log.e("Glide", "Load failed for ${drawable.data}")
//                }
//            })
//    }



    private fun drawImage(canvas: Canvas, drawable: FView.Drawable, layoutRect: Rect, fView: FView) {
        val imageView = ImageView(context)
        GlideApp.with(context)
            .asBitmap()
            .load(drawable.data)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                    val bitmap = Bitmap.createScaledBitmap(resource, layoutRect.width(), layoutRect.height(), false)
                    canvas.drawBitmap(bitmap, layoutRect.left.toFloat(), layoutRect.top.toFloat(), null)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun convertToPixels(dimen: FView.Dimension): Int {
        return when (dimen.value) {
            -1 -> ViewGroup.LayoutParams.MATCH_PARENT
            -2 -> ViewGroup.LayoutParams.WRAP_CONTENT
            else -> when (dimen.unit) {
                UnitConfig.PX -> dimen.value
                UnitConfig.DP -> (dimen.value * Resources.getSystem().displayMetrics.density).toInt()
                else -> dimen.value
            }
        }
    }
}
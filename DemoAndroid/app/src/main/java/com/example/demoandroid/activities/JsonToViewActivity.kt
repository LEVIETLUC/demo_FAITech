package com.example.demoandroid.activities

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroid.CustomImageView
import com.example.demoandroid.CustomTextView
import com.example.demoandroid.Json2View
import com.example.demoandroid.R
import com.example.demoandroid.adapters.MultiTypeAdapter
import com.example.demoandroid.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject



class JsonToViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            var jsonObject: JSONObject
            try {
                jsonObject = JSONObject(readFile("sampleUi.json"))
                withContext(Dispatchers.Main) {
                    Log.d("JsonToViewActivity", "jsonObject: $jsonObject")
                    val rootViewData = Json2View.parseJsonToViewData(jsonObject.toString())
                    Log.d("JsonToViewActivity", "rootViewData: $rootViewData")
                    val rootView = createView(this@JsonToViewActivity, rootViewData)
                    setContentView(rootView)
                }
            } catch (e: Exception) {
                jsonObject = JSONObject()
                withContext(Dispatchers.Main) {
                    Log.e("JsonToViewActivity", "Error reading JSON file", e)
                }
            }
        }


    }

    private fun readFile(fileName: String): String {
        return try {
            applicationContext.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
    companion object {

        fun createView(context: Context, viewData: ViewData): View {
            return when (viewData.viewType) {
                ViewTypeConfig.RECYCLER_VIEW -> handleRecyclerView(context, viewData)
                ViewTypeConfig.VIEW_GROUP -> handleViewGroup(context, viewData)
                ViewTypeConfig.TEXT_VIEW -> handleTextView(context, viewData)
                ViewTypeConfig.IMAGE_VIEW -> handleImageView(context, viewData)
            }
        }


        private fun handleImageView(context: Context, viewData: ViewData): View {
            val customImageView = CustomImageView(context).apply {
                val layoutParams = FrameLayout.LayoutParams(
                    convertToPixels(viewData.props.width),
                    convertToPixels(viewData.props.height)
                ).apply {
                    gravity = viewData.props.layoutGravity?.let {
                        parseGravity(it)
                    } ?: parseGravity(emptySet())
                }
                this.layoutParams = layoutParams
                setBitmapFromResource(R.drawable.demo_img)
                setLayoutWidth(convertToPixels(viewData.props.width))
                setLayoutHeight(convertToPixels(viewData.props.height))
                viewData.props.layoutGravity?.let { parseGravity(it) }?.let { setLayoutGravity(it) }

            }
            return customImageView

        }

        private fun handleTextView(context: Context, viewData: ViewData): View {
            val customTextView = CustomTextView(context, viewData.props.textView?.text ?: "null").apply {
                val layoutParams = FrameLayout.LayoutParams(
                    convertToPixels(viewData.props.width),
                    convertToPixels(viewData.props.height)
                ).apply {
                    gravity = viewData.props.layoutGravity?.let {
                        parseGravity(it)
                    } ?: parseGravity(emptySet())
                }
                this.layoutParams = layoutParams
                setLayoutWidth(convertToPixels(viewData.props.width))
                setLayoutHeight(convertToPixels(viewData.props.height))
                setTextColor(Color.parseColor(viewData.props.textView?.color ?: "#000000"))
                setBackGroundColor(Color.parseColor(viewData.props.background?.color ?: "#FFFFFF"))
                viewData.props.gravity?.let { parseGravity(it) }?.let { setGravity(it) }
                viewData.props.layoutGravity?.let { parseGravity(it) }?.let { setLayoutGravity(it) }
                viewData.props.textView?.fontSize?.value?.let { setTextSize(it.toFloat()) }
                viewData.props.textView?.fontStyle?.let { parseTextStyle(it) }?.let { setTextStyle(it) }
            }
            return customTextView
        }

        private fun handleViewGroup(context: Context, viewData: ViewData): View {
            val viewGroup = FrameLayout(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    convertToPixels(viewData.props.width),
                    convertToPixels(viewData.props.height)
                ).apply {
                    gravity = parseGravity(viewData.props.gravity ?: emptySet())
                }
                setBackgroundColor(Color.parseColor(viewData.props.background?.color ?: "#FFFFFF"))
            }

            viewData.children?.forEach { childLayout ->
                val childView = createView(context, childLayout)
                viewGroup.addView(childView)
            }

            return viewGroup

        }

        private fun handleRecyclerView(context: Context, viewData: ViewData): View {
            val recyclerView = RecyclerView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    convertToPixels(viewData.props.width),
                    convertToPixels(viewData.props.height)
                ).apply {
                    gravity = parseGravity(viewData.props.gravity ?: emptySet())
                }
                setBackgroundColor(Color.parseColor(viewData.props.background?.color))

                layoutManager = if (viewData.props.orientation == OrientationConfig.VERTICAL) {
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                } else {
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }

                adapter = viewData.children?.let { MultiTypeAdapter(it) }
            }

            return recyclerView

        }


        private fun convertToPixels(dimen: Dimension): Int {
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

        private fun parseGravity(gravityFlags: Set<GravityConfig?>): Int {
            return gravityFlags.fold(0) { acc, gravityFlag ->
                acc or when (gravityFlag) {
                    GravityConfig.LEFT -> Gravity.LEFT
                    GravityConfig.TOP -> Gravity.TOP
                    GravityConfig.RIGHT -> Gravity.RIGHT
                    GravityConfig.BOTTOM -> Gravity.BOTTOM
                    GravityConfig.CENTER -> Gravity.CENTER
                    null -> Gravity.NO_GRAVITY
                }
            }
        }

        private fun parseTextStyle(fontStyle: FontStyleConfig?): Int {
            return when (fontStyle) {
                FontStyleConfig.NORMAL -> Typeface.NORMAL
                FontStyleConfig.BOLD -> Typeface.BOLD
                FontStyleConfig.ITALIC -> Typeface.ITALIC
                FontStyleConfig.BOLD_ITALIC -> Typeface.BOLD_ITALIC
                null -> Typeface.NORMAL
            }
        }

//        private fun loadImageBitmap(resource: String): Bitmap? {
//            val resId = resources.getIdentifier(resource, "drawable", packageName)
//            return BitmapFactory.decodeResource(resources, resId)
//        }
    }
}
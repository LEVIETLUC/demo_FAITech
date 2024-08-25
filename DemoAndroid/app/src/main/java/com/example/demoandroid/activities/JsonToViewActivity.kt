package com.example.demoandroid.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroid.CustomImageView
import com.example.demoandroid.CustomTextView
import com.example.demoandroid.adapters.TextViewAdapter
import com.example.demoandroid.data.Dimension
import com.example.demoandroid.data.ViewData
import com.example.demoandroid.parseJsonToViewData
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
                    val rootViewData = parseJsonToViewData(jsonObject.toString())
                    val rootView = createView(rootViewData?: return@withContext)
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
        return     try {
            applicationContext.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private fun createView(viewData: ViewData): View {
        return when (viewData.viewType) {
            1 -> { // RecyclerView
                RecyclerView(this).apply {
                    viewData.props.let { props ->
                        layoutParams = ViewGroup.LayoutParams(
                            convertToPixels(props.width),
                            convertToPixels(props.height)
                        )
                        setBackgroundColor(Color.parseColor(props.background?.color ?: "#FFFFFF"))
                    }
                    layoutManager = LinearLayoutManager(this@JsonToViewActivity)
                    adapter = TextViewAdapter(viewData.children ?: listOf())
                    val itemDecoration = DividerItemDecoration(this@JsonToViewActivity, DividerItemDecoration.VERTICAL)
                    addItemDecoration(itemDecoration)
                }
            }
            2 -> { // ViewGroup
                LinearLayout(this).apply {
                    viewData.props.let { props ->
                        orientation = if (props.orientation == 1) LinearLayout.VERTICAL else LinearLayout.HORIZONTAL
                        layoutParams = ViewGroup.LayoutParams(
                            convertToPixels(props.width),
                            convertToPixels(props.height)
                        ).apply { gravity = convertCustomGravity(props.gravity ?: 0)}

                        setBackgroundColor(Color.parseColor(props.background?.color ?: "#FFFFFF"))
                    }
                    // Recursively create and add child views
                    viewData.children?.forEach { child ->
                        addView(createView(child))
                    }
                }
            }
            3 -> { // CustomTextView
                CustomTextView(this).apply {
                    viewData.props.let { props ->
                        Log.d("JsonToViewActivity", "props: ${props.textView?.text ?: "Default Text"}")
                        setText(props.textView?.text ?: "Default Text")
                        textSize = props.textView?.fontSize?.toFloat() ?: 50f
                        setTextColor(Color.parseColor(props.textView?.color ?: "#000000"))
                        setFontStyle(props.textView?.fontStyle ?: Typeface.NORMAL)
                        layoutParams = ViewGroup.LayoutParams(
                            convertToPixels(props.width),
                            convertToPixels(props.height)
                        ).apply {
                            layoutGravity = convertCustomGravity(props.layoutGravity ?: 0)

                        }
                    }
                }
            }
            4 -> { // CustomImageView
                CustomImageView(this).apply {
                    setImageBitmap(loadImageBitmap(viewData.props.imageView?.resource ?: ""))
                    layoutParams = ViewGroup.LayoutParams(
                        viewData.props.width.let { convertToPixels(it) },
                        viewData.props.height.let { convertToPixels(it) }
                    )
                }
            }
            else -> throw IllegalArgumentException("Unsupported view type: ${viewData.viewType}")
        }
    }

    private fun convertToPixels(dimen: Dimension): Int {
        return when (dimen.value) {
            -1 -> ViewGroup.LayoutParams.MATCH_PARENT
            -2 -> ViewGroup.LayoutParams.WRAP_CONTENT
            else -> if (dimen.unit == 1) {
                (dimen.value * this.resources.displayMetrics.density).toInt()
            } else {
                dimen.value
            }
        }
    }

    private fun convertCustomGravity(customGravity: Int): Int {
        return when (customGravity) {
            0 -> Gravity.LEFT
            1 -> Gravity.TOP
            2 -> Gravity.RIGHT
            3 -> Gravity.BOTTOM
            4 -> Gravity.CENTER
            5 -> Gravity.LEFT and Gravity.CENTER_VERTICAL
            6 -> Gravity.RIGHT and Gravity.CENTER_VERTICAL
            7 -> Gravity.CENTER_HORIZONTAL and Gravity.TOP
            8 -> Gravity.CENTER_HORIZONTAL and Gravity.BOTTOM
            else -> Gravity.NO_GRAVITY
        }
    }


    private fun loadImageBitmap(resource: String): Bitmap {
        val resId = resources.getIdentifier(resource, "drawable", packageName)
        return BitmapFactory.decodeResource(resources, resId)
    }
}
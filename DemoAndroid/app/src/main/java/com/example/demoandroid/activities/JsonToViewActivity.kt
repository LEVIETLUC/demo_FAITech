package com.example.demoandroid.activities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroid.CustomImageView
import com.example.demoandroid.CustomTextView
import com.example.demoandroid.data.Dimension
import com.example.demoandroid.data.ViewData
import com.example.demoandroid.parseJsonToViewData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


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
                    viewData.props?.let { props ->
                        layoutParams = ViewGroup.LayoutParams(
                            convertToPixels(props.width),
                            convertToPixels(props.height)
                        )
                        setBackgroundColor(Color.parseColor(props.background?.color ?: "#FFFFFF"))
                    }
                    // Set up the RecyclerView (e.g., LayoutManager, Adapter)
                    // This example assumes an adapter and layout manager are already implemented
                }
            }
            2 -> { // ViewGroup (e.g., LinearLayout)
                LinearLayout(this).apply {
                    viewData.props.let { props ->
                        orientation = if (props.orientation == 1) LinearLayout.VERTICAL else LinearLayout.HORIZONTAL
                        layoutParams = ViewGroup.LayoutParams(
                            convertToPixels(props.width),
                            convertToPixels(props.height)
                        )
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
                    viewData.props?.let { props ->
                        setText(viewData.text ?: "Default Text")
                        textSize = props.textView?.textSize?.toFloat() ?: 50f
                        setTextColor(Color.parseColor(props.textView?.color ?: "#000000"))
                        layoutParams = ViewGroup.LayoutParams(
                            convertToPixels(props.width),
                            convertToPixels(props.height)
                        )
                    }
                }
            }
            4 -> { // CustomImageView
                CustomImageView(this).apply {
                    viewData.imageUrl?.let {
                        // Assuming image loading is handled through this method
                        setImageBitmap(loadImageBitmap(it))
                    }
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
        return if (dimen.unit == 1) {
            (dimen.value * this.resources.displayMetrics.density).toInt()
        } else {
            dimen.value
        }
    }

    private fun loadImageResource(resource: String): Int {
        // This is a stub. You would replace this with actual image resource loading logic
        return this.resources.getIdentifier(resource, null, this.packageName)
    }

    private fun loadImageBitmap( resource: String ) : Bitmap {
        // This is a stub. You would replace this with actual image bitmap loading logic
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }
}
package com.example.demoandroid

import android.util.Log
import com.example.demoandroid.data.*
import org.json.JSONArray
import org.json.JSONObject


object Json2View {


    fun parseJsonToViewData(json: String): ViewData {
        val jsonObject = JSONObject(json)
        if (jsonObject.has("props")) {
            parseViewProps(jsonObject.getJSONObject("props"))
            Log.d("JsonToViewActivity", "props: ${jsonObject.getJSONObject("props")}")
        }
        return parseViewData(jsonObject)
    }

    private fun parseViewData(jsonObject: JSONObject): ViewData {
        val viewType = ViewTypeConfig.entries.first { it.value == jsonObject.getInt("viewType") }
        val props = parseViewProps(jsonObject.getJSONObject("props"))
        val children = if (jsonObject.has("children")) parseChildren(jsonObject.getJSONArray("children")) else null
        return ViewData(viewType, props, children)
    }

    private fun parseViewProps(jsonObject: JSONObject): ViewProps {
        Log.d("JsonToViewActivity", "jsonObject: ${jsonObject.getJSONObject("width")}")
        val width = jsonObject.getJSONObject("width").let { it ->
            val value = it.getInt("value")
            val unit = UnitConfig.entries
                .first { it.value == jsonObject.getJSONObject("width").getInt("unit") }
            Dimension(value, unit)
        }
        Log.d("JsonToViewActivity", "jsonObject: $width")
        val height = jsonObject.getJSONObject("height").let { it ->
            val value = it.getInt("value")
            val unit = UnitConfig.entries
                .first { it.value == jsonObject.getJSONObject("width").getInt("unit") }
            Dimension(value, unit)
        }
        val background = if (jsonObject.has("background")) {
            jsonObject.getJSONObject("background").let {
                Background(it.getInt("type"), it.getString("color"))
            }
        } else null

        val gravity = if (jsonObject.has("gravity")) {
            parseGravityConfig(jsonObject.getInt("gravity"))
        } else null
        val layoutGravity = if (jsonObject.has("layoutGravity")) {
            parseGravityConfig(jsonObject.getInt("layoutGravity"))
        } else null
        val orientation = if (jsonObject.has("orientation")) {
            OrientationConfig.entries.first { it.value == jsonObject.getInt("orientation") }
        } else null
        val layoutType = if (jsonObject.has("layoutType")) {
            LayoutTypeConfig.entries.first { it.value == jsonObject.getInt("layoutType") }
        } else null
        val textView = if (jsonObject.has("textView")) {
            parseTextView(jsonObject.getJSONObject("textView"))
        } else null
        val imageView = if (jsonObject.has("imageView")) {
            parseImageView(jsonObject.getJSONObject("imageView"))
        } else null
        return ViewProps(
            width,
            height,
            background,
            gravity,
            layoutGravity,
            orientation,
            layoutType,
            textView,
            imageView
        )
    }

    private fun parseChildren(jsonArray: JSONArray): List<ViewData> {
        val children = mutableListOf<ViewData>()
        for (i in 0 until jsonArray.length()) {
            val child = parseViewData(jsonArray.getJSONObject(i))
            children.add(child)
        }
        return children
    }

    private fun parseTextView(jsonObject: JSONObject): TextView {
        val text = jsonObject.getString("text")
        val fontSize = if (jsonObject.has("textSize")) {
            val value = jsonObject.getJSONObject("textSize").getInt("value")
            val unit = UnitConfig.entries
                .first { it.value == jsonObject.getJSONObject("textSize").getInt("unit") }
            Dimension(value, unit)
        } else null
        val fontStyle = if (jsonObject.has("textStyle")) {
            FontStyleConfig.entries.first { it.value == jsonObject.getInt("textStyle") }
        } else null
        val color = if (jsonObject.has("color")) {
            jsonObject.getString("color")
        } else null
        return TextView(
            text,
            fontSize,
            fontStyle,
            color
        )
    }

    private fun parseImageView(jsonObject: JSONObject): ImageView {
        val resource = jsonObject.getString("resource")
        return ImageView(resource)
    }

    private fun parseGravityConfig(value: Int): Set<GravityConfig?> {
        return GravityConfig.entries.filter {
            it.value == value
        }.toSet()

    }
}

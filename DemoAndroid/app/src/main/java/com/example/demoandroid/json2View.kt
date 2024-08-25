package com.example.demoandroid

import android.util.Log
import com.example.demoandroid.data.*
import org.json.JSONArray
import org.json.JSONObject

fun parseJsonToViewData(json: String): ViewData? {
    val jsonObject = JSONObject(json)
    if (jsonObject.has("props")) {
        parseViewProps(jsonObject.getJSONObject("props"))
        Log.d("JsonToViewActivity", "props: ${jsonObject.getJSONObject("props")}")
    }
    return parseViewData(jsonObject)
}

private fun parseViewData(jsonObject: JSONObject): ViewData? {
    val viewType = jsonObject.getInt("viewType")
    val props = if (jsonObject.has("props")) parseViewProps(jsonObject.getJSONObject("props")) else null
    val children = if (jsonObject.has("children")) parseChildren(jsonObject.getJSONArray("children")) else null

    Log.d("JsonToViewActivity", "viewType: $viewType, props: $props, children: $children")

    return props?.let { ViewData(viewType, it, children) }
}

private fun parseViewProps(jsonObject: JSONObject): ViewProps {
    Log.d("JsonToViewActivity", "jsonObject: ${jsonObject.getJSONObject("width")}")
    val width = jsonObject.getJSONObject("width").let {
        Dimension(it.getInt("value"), it.getInt("unit"))
    }
    Log.d("JsonToViewActivity", "jsonObject: $width")
    val height = jsonObject.getJSONObject("height").let {
        Dimension(it.getInt("value"), it.getInt("unit"))
    }
    val background = if (jsonObject.has("background")) {
        jsonObject.getJSONObject("background").let {
            Background(it.getInt("type"), it.getString("color"))
        }
    } else null

    val gravity = if (jsonObject.has("gravity")) jsonObject.getInt("gravity") else null
    val layoutGravity = if (jsonObject.has("layoutGravity")) jsonObject.getInt("layoutGravity") else null
    val orientation = if (jsonObject.has("orientation")) jsonObject.getInt("orientation") else null
    val layoutType = if (jsonObject.has("layoutType")) jsonObject.getInt("layoutType") else null
    val textView = if (jsonObject.has("textView")) {
        jsonObject.getJSONObject("textView").let {
            TextView(it.getString("text"), it.getInt("fontSize"), if (it.has("fontStyle")) it.getInt("fontStyle") else 0, it.getString("color"))
        }
    } else null
    val imageView = if (jsonObject.has("imageView")) {
        ImageView(jsonObject.getJSONObject("imageView").getString("resource"))
    } else null
    return ViewProps(width, height, background, gravity, layoutGravity, orientation, layoutType, textView, imageView )
}

private fun parseChildren(jsonArray: JSONArray): List<ViewData> {
    val children = mutableListOf<ViewData>()
    for (i in 0 until jsonArray.length()) {
        val child = parseViewData(jsonArray.getJSONObject(i))
        if (child != null) {
            children.add(child)
        }
    }
    return children
}

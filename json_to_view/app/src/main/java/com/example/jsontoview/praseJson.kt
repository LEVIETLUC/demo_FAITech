package com.example.jsontoview
import com.google.gson.Gson
import com.google.gson.JsonObject

object PraseJson {
    fun parseJsonToViewData(json: String): FView {
        val gson = Gson()
        return gson.fromJson(json, FView::class.java)
    }
}
package com.example.demoandroid.data

data class ViewProps(
    val width: Dimension,
    val height: Dimension,
    val background: Background?,
    val gravity: Int?,
    val layoutGravity: Int?,
    val orientation: Int?,
    val layoutType: Int?,
    val textView: TextView?,
    val imageView: ImageView?,
)

data class Dimension(
    val value: Int,
    val unit: Int
)

data class Background(
    val type: Int,
    val color: String
)

data class TextView(
    val text: String,
    val fontSize: Int,
    val fontStyle :Int?,
    val color: String
)

data class ImageView(
    val resource: String
)

data class ViewData(
    val viewType: Int,
    val props: ViewProps,
    val children: List<ViewData>?,
)

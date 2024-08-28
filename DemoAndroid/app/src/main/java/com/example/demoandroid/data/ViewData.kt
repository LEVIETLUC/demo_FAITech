package com.example.demoandroid.data

enum class ViewTypeConfig(val value: Int) {
    RECYCLER_VIEW(1),
    VIEW_GROUP(2),
    TEXT_VIEW(3),
    IMAGE_VIEW(4)
}

enum class UnitConfig(val value: Int) {
    DP(1),
    PX(2)
}

enum class GravityConfig(val value: Int) {
    LEFT(0),
    TOP(1),
    RIGHT(2),
    BOTTOM(3),
    CENTER(4)
}

enum class OrientationConfig(val value: Int) {
    HORIZONTAL(0),
    VERTICAL(1)
}

enum class LayoutTypeConfig(val value: Int) {
    CONTINUES(0),
    STACK(1)
}

enum class FontStyleConfig(val value: Int) {
    NORMAL(0),
    BOLD(1),
    ITALIC(2),
    BOLD_ITALIC(3)
}


data class ViewProps(
    val width: Dimension,
    val height: Dimension,
    val background: Background?,
    val gravity: Set<GravityConfig?>?,
    val layoutGravity: Set<GravityConfig?>?,
    val orientation: OrientationConfig?,
    val layoutType: LayoutTypeConfig?,
    val textView: TextView?,
    val imageView: ImageView?,
)

data class Dimension(
    val value: Int,
    val unit: UnitConfig
)

data class Background(
    val type: Int,
    val color: String
)

data class TextView(
    val text: String,
    val fontSize: Dimension?,
    val fontStyle : FontStyleConfig?,
    val color: String?
)

data class ImageView(
    val resource: String
)

data class ViewData(
    val viewType: ViewTypeConfig,
    val props: ViewProps,
    val children: List<ViewData>?,
)


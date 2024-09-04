package com.example.jsontoview

import android.graphics.Bitmap
import android.graphics.Rect
import com.google.gson.annotations.SerializedName

enum class ViewTypeConfig(val value: Int) {
    @SerializedName("1") VIEW_GROUP(1),
    @SerializedName("2") RECYCLER_VIEW(2)
}

enum class TypeConfig(val value: Int) {
    @SerializedName("1") TEXT_VIEW(1),
    @SerializedName("2") IMAGE_VIEW(2)
}

enum class UnitConfig(val value: Int) {
    @SerializedName("1") DP(1),
    @SerializedName("2") PX(2)
}

enum class GravityConfig(val value: Int) {
    @SerializedName("0") LEFT(0),
    @SerializedName("1") TOP(1),
    @SerializedName("2") RIGHT(2),
    @SerializedName("3") BOTTOM(3),
    @SerializedName("4") CENTER(4)
}

enum class OrientationConfig(val value: Int) {
    @SerializedName("0") HORIZONTAL(0),
    @SerializedName("1") VERTICAL(1)
}

enum class LayoutTypeConfig(val value: Int) {
    @SerializedName("0") CONTINUES(0),
    @SerializedName("1") STACK(1)
}

enum class FontStyleConfig(val value: Int) {
    @SerializedName("0") NORMAL(0),
    @SerializedName("1") BOLD(1),
    @SerializedName("2") ITALIC(2),
    @SerializedName("3") BOLD_ITALIC(3)
}

data class FView(
    @SerializedName("viewType")
    var viewType: ViewTypeConfig? = null,

    @SerializedName("props")
    var props: ViewProps? = null,

    @SerializedName("children")
    var children: List<FView>? = null
) {
    lateinit var layoutRect: Rect
    var measuredHeight: Int = 0
    var measuredWidth: Int = 0
    var bitmap: Bitmap? = null


    data class ViewProps(
        @SerializedName("width")
        var width: Dimension,

        @SerializedName("height")
        var height: Dimension,

        @SerializedName("background")
        var background: Background? = null,

        @SerializedName("padding")
        var padding: Padding? = null,

        @SerializedName("margin")
        val margin: Margin? = null,

        @SerializedName("gravity")
        var gravity: GravityConfig? = null,

        @SerializedName("layoutGravity")
        var layoutGravity: GravityConfig? = null,

        @SerializedName("orientation")
        var orientation: OrientationConfig? = null,

        @SerializedName("layoutType")
        var layoutType: LayoutTypeConfig? = null,

        @SerializedName("drawable")
        var drawable: Drawable? = null
    )

    data class Background(
        @SerializedName("type")
        var type: Int = 0,

        @SerializedName("color")
        var color: String = ""
    )

    data class Dimension(
        @SerializedName("value")
        var value: Int,

        @SerializedName("unit")
        var unit: UnitConfig
    )

    data class Padding(
        @SerializedName("left")
        var left: Int,

        @SerializedName("top")
        var top: Int,

        @SerializedName("right")
        var right: Int,

        @SerializedName("bottom")
        var bottom: Int
    )

    data class Margin(
        @SerializedName("left")
        var left: Int,

        @SerializedName("top")
        var top: Int,

        @SerializedName("right")
        var right: Int,

        @SerializedName("bottom")
        var bottom: Int
    )

    data class Drawable(
        @SerializedName("type")
        var type: TypeConfig,

        @SerializedName("data")
        var data: String,

        @SerializedName("props")
        var props: DrawableProps? = null
    )

    data class DrawableProps(
        @SerializedName("textSize")
        var textSize: Float? = null,

        @SerializedName("textColor")
        var textColor: String? = null,

        @SerializedName("scaleType")
        var scaleType: Int? = null
    )
}


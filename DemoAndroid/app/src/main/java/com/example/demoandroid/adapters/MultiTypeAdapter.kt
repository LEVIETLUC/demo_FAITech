package com.example.demoandroid.adapters

import android.graphics.Color
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroid.CustomImageView
import com.example.demoandroid.CustomTextView
import com.example.demoandroid.data.ViewData

class MultiTypeAdapter(private val items: List<ViewData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_TEXT = 3
        const val VIEW_TYPE_IMAGE = 4
    }

    class TextViewHolder(val customTextView: CustomTextView) : RecyclerView.ViewHolder(customTextView)
    class ImageViewHolder(val customImageView: CustomImageView) : RecyclerView.ViewHolder(customImageView)

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TEXT -> {
                val customTextView = CustomTextView(parent.context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
                TextViewHolder(customTextView)
            }
            VIEW_TYPE_IMAGE -> {
                val customImageView = CustomImageView(parent.context)
                ImageViewHolder(customImageView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder.itemViewType) {
            VIEW_TYPE_TEXT -> {
                val textViewHolder = holder as TextViewHolder
                val textProps = item.props.textView
                textViewHolder.customTextView.apply {
                    setText(textProps?.text ?: "")
                    textSize = textProps?.fontSize?.toFloat() ?: 18f
                    setTextColor(Color.parseColor(textProps?.color ?: "#000000"))
                    setBackgroundColor(Color.parseColor("#FFFFFF"))
                }
            }
            VIEW_TYPE_IMAGE -> {
                val imageViewHolder = holder as ImageViewHolder
                val resId = holder.itemView.context.resources.getIdentifier(
                    item.props.imageView?.resource,
                    "drawable",
                    holder.itemView.context.packageName
                )
                val bitmap = resId.let { imageViewHolder.itemView.context.getDrawable(it)?.toBitmap() }
                if (bitmap != null) {
                    imageViewHolder.customImageView.setImageBitmap(bitmap)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}

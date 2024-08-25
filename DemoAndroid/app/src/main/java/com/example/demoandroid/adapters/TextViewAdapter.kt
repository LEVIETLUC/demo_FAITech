package com.example.demoandroid.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroid.CustomTextView
import com.example.demoandroid.data.ViewData
import android.graphics.Color


class TextViewAdapter(private val items: List<ViewData>) : RecyclerView.Adapter<TextViewAdapter.TextViewHolder>() {
    class TextViewHolder(val customTextView: CustomTextView) : RecyclerView.ViewHolder(customTextView)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TextViewHolder {
        val customTextView = CustomTextView(parent.context)
        return TextViewHolder(customTextView)    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val item = items[position]
        holder.customTextView.apply {
            // Assuming item.props.textView is not null and has the data
            setText(item.props.textView?.text ?: "")
            textSize = item.props.textView?.fontSize?.toFloat() ?: 18f
            setTextColor(Color.parseColor(item.props.textView?.color ?: "#000000"))
        }    }

}
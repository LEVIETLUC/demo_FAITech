package com.example.demoandroid.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroid.data.ViewData
import com.example.demoandroid.activities.JsonToViewActivity.Companion.createView


class MultiTypeAdapter(private val children: List<ViewData>) :
    RecyclerView.Adapter<MultiTypeAdapter.ViewHolder>() {

    inner class ViewHolder(val view: android.view.View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val view = createView(context, children[viewType])

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind dữ liệu vào ViewHolder

    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun getItemViewType(position: Int): Int {
        // Sử dụng viewType từ LayoutConfig để quyết định kiểu view của item
        return position
    }
}

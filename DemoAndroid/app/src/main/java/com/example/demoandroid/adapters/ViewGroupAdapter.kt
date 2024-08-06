package com.example.demoandroid.adapters

import OnViewGroupClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroid.R
import com.example.demoandroid.data.ViewGroupType

class ViewGroupAdapter(
    private val viewGroupList: List<ViewGroupType>,
    private val listener: OnViewGroupClickListener
) : RecyclerView.Adapter<ViewGroupAdapter.ViewGroupViewHolder>() {

    inner class ViewGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewGroupName: TextView = itemView.findViewById(R.id.view_group_name_txt)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onViewGroupClick(viewGroupList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewGroupViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view_group, parent, false)
        return ViewGroupViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewGroupViewHolder, position: Int) {
        val currentItem = viewGroupList[position]
        holder.viewGroupName.text = currentItem.title
    }

    override fun getItemCount() = viewGroupList.size
}
package com.example.demoandroid.adapters

import OnFeatureClickListener
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroid.R
import com.example.demoandroid.data.FeatureItem

class FeatureAdapter(
    private val featureList: List<FeatureItem>,
    private val listener: OnFeatureClickListener
) : RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder>() {

    inner class FeatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val featureName: TextView = itemView.findViewById(R.id.feat_name_txt)
        val featureDescription: TextView = itemView.findViewById(R.id.feat_desc_txt)
        private val editButton: ImageButton = itemView.findViewById(R.id.edit_btn)

        init {
            editButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onFeatureClick(featureList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_feat_option, parent, false)
        return FeatureViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        val currentItem = featureList[position]
        holder.featureName.text = currentItem.title
        holder.featureDescription.text = currentItem.description
    }

    override fun getItemCount() = featureList.size

}
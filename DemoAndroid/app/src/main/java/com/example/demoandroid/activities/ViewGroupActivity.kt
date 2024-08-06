package com.example.demoandroid.activities

import OnViewGroupClickListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroid.R
import com.example.demoandroid.adapters.FeatureAdapter
import com.example.demoandroid.adapters.ViewGroupAdapter
import com.example.demoandroid.data.Feature
import com.example.demoandroid.data.ViewGroupType

class ViewGroupActivity : AppCompatActivity(), OnViewGroupClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_group)

        val viewGroupList = listOf(
            ViewGroupType("LinearLayout", "LinearLayoutActivity"),
            ViewGroupType("RelativeLayout", "RelativeLayoutActivity"),
            ViewGroupType("FrameLayout", "FrameLayoutActivity"),
            ViewGroupType("ConstraintLayout", "ConstraintLayoutActivity"),
        )

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_btn)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ViewGroupAdapter(viewGroupList, this)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }

    override fun onViewGroupClick(viewGroupType: ViewGroupType) {
        val intent = Intent(this, Class.forName("com.example.demoandroid.activities.viewgroup.${viewGroupType.namePage}"))
        intent.putExtra("ViewGroup_NAME", viewGroupType.title)
        startActivity(intent)
    }
}
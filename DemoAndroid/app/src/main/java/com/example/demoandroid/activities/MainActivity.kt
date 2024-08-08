package com.example.demoandroid.activities

import OnFeatureClickListener
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoandroid.R
import com.example.demoandroid.adapters.FeatureAdapter
import com.example.demoandroid.data.Feature

class MainActivity : AppCompatActivity(), OnFeatureClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val featureList = listOf(
            Feature("Hello + Name ",
                    "- Thiết kế giao diện bằng XML " +
                    "\n- Truy cập TextView theo id để hiển thị 'Hello + name'" , "HelloTextViewActivity"),
            Feature("TextView, TextInput dùng XML",
                    "- Thiết kế giao diện bằng XML, cần anh xạ tới các widget của XML" +
                    "\n- Lấy TextView của trang đầu truyền sang trang 2 để làm hint text, sau đó lấy  dữ liệu đã nhập ở trang 2 trả về cho trang 1", "TextViewActivity"),
            Feature("TextView, TextInput dùng Component có sẵn",
                    "- Thiết kế bằng component có sẵn của Kotlin, khởi tạo trực tiếp các widget trong Kotlin" +
                    "\n- Lấy TextView của trang đầu truyền sang trang 2 để làm hint text, sau đó lấy  dữ liệu đã nhập ở trang 2 trả về cho trang 1", "TextViewComponent"),
            Feature("Load Image trong resource",
                    "- Thiết kế giao diện bằng XML" +
                    "\n- Sử dụng thẻ ImageView và gán đường dẫn vào trong android:src để hiện thị hình ảnh từ resource", "ImageBaseActivity"),
            Feature("ViewGroup",
                    "- Hiển thị các loại ViewGroup cơ bản" +
                    "\n- Sử dụng RecyclerView để hiển thị danh sách các ViewGroup cơ bản", "ViewGroupActivity"),
            Feature("Custom view",
                    "- Tạo TextView + Button + ImageView trong CustomView" +
                    "\n- Override onDraw để vẽ tất cả widget lên 1 view, sử dụng BoringLayout để xác định vị trí TextView", "CustomViewActivity"),

        )

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FeatureAdapter(featureList, this)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }

    override fun onFeatureClick(feature: Feature) {
        val intent = Intent(this, Class.forName("com.example.demoandroid.activities.${feature.namePage}"))
        intent.putExtra("FEATURE_NAME", feature.title)
        startActivity(intent)
    }
}
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
import com.example.demoandroid.data.FeatureItem

class MainActivity : AppCompatActivity(), OnFeatureClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val featureList = listOf(
            FeatureItem("Hello + Name ",
                    "- Thiết kế giao diện bằng XML " +
                    "\n- Truy cập TextView theo id để hiển thị 'Hello + name'" , "HelloTextViewActivity"),
            FeatureItem("TextView, TextInput dùng XML",
                    "- Thiết kế giao diện bằng XML, cần anh xạ tới các widget của XML" +
                    "\n- Lấy TextView của trang đầu truyền sang trang 2 để làm hint text, sau đó lấy  dữ liệu đã nhập ở trang 2 trả về cho trang 1", "TextViewActivity"),
            FeatureItem("TextView, TextInput dùng Component có sẵn",
                    "- Thiết kế bằng component có sẵn của Kotlin, khởi tạo trực tiếp các widget trong Kotlin" +
                    "\n- Lấy TextView của trang đầu truyền sang trang 2 để làm hint text, sau đó lấy  dữ liệu đã nhập ở trang 2 trả về cho trang 1", "TextViewComponent"),
            FeatureItem("Load Image trong resource",
                    "- Thiết kế giao diện bằng XML" +
                    "\n- Sử dụng thẻ ImageView và gán đường dẫn vào trong android:src để hiện thị hình ảnh từ resource", "ImageBaseActivity"),
        )

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FeatureAdapter(featureList, this)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }

    override fun onFeatureClick(feature: FeatureItem) {
        val intent = Intent(this, Class.forName("com.example.demoandroid.activities.${feature.namePage}"))
        intent.putExtra("FEATURE_NAME", feature.title)
        startActivity(intent)
    }
}
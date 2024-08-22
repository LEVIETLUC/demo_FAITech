package com.example.demoandroid.activities

import OnFeatureClickListener
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ScrollView
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
            Feature("Handle event touch",
                    "- Xử lý sự kiện chạm trên View và ViewGroup" +
                    "\n- Sử dụng onTouchEvent, onInterceptTouchEvent, dispatchTouchEvent để xử lý sự kiện chạm và khi view con gọi requestDisallowInterceptTouchEvent để yêu cầu không chặn hàm đó có được thục hiện hay không ", "HandleEventViewGroupActivity"),
            Feature("ScrollView",
                    "- Sử dụng ScrollView để cuộn nội dung" +
                    "\n- Xử lí 2 ScrollView lồng nhau", "ScrollViewActivity"),
            Feature("Foreground Service",
                    "- Tạo Foreground Service" +
                    "\n- Cần có thông báo khi chạy foreground và sẽ chạy liên tục cho đến khi buộc bị tắt ", "ForegroundServiceActivity"),
            Feature("Background Service",
                "- Tạo Foreground Service" +
                "\n- Không cần thông báo cho người dùng khi chạy background và chỉ chạy tầm hơn 1 phút là sẽ bị hệ thống kill" , "BackgroundServiceActivity"),
            Feature("Broadcast Receiver",
                "- Tạo Broadcast Receiver" +
                "\n- Nhận sự kiện khi mạng thay đổi và hiển thị thông báo", "BroadcastReceiverActivity"),
            Feature("Load Image from URL",
                "- Load ảnh từ URL" +
                "\n- Nhập URL ảnh vào và hiển thị ảnh", "LoadImageActivity"),
            Feature("Parse JSON",
                "- Parse JSON từ file" +
                "\n- Và parse thành Map, sau đó convert ngược lại json mà không sử dụng lib có sẵn " +
                "\n- Bằng các di duyệt theo các ký tự của cú pháp json để chỉ lấy ra key và value", "ParseJsonActivity"),
            Feature("Handle File",
                "- xử lý đọc file và ghi vào 10 file khác nhau từ file đó sử dụng InputStream/Outputstream " +
                "\n- Kết hợp thêm buffered để truyền dữ liệu vào bộ đệm và đọc từ bộ đệm đó và seek trong lớp RandomAccessFile để có thể di chuyển con trỏ tới vị trì cần đọc ", "HandleFileActivity"),

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
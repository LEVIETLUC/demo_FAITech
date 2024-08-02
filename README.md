# demo_FAITech

## XML vs Jetpack Compose

| So sánh | XML | Jetpack compose |
| ------------- | ------------- | ------------- |
| Mô hình lập trình | Imperative Programming | Declarative Programming  |
| Quản lý trạng thái | Quan tâm tới các bước thực hiện cụ thể | Chỉ quan tâm tới đầu vào và đầu ra của vấn đề |
| Cấu trúc | file XML cho phần giao diện, file java/kotlin cho phần handle event | viết tất cả bằng file kotlin  |
| Đọc, viết code | Phức tạp, khó khăn hơn | dễ dàng, ngắn gọn hơn  |
| Tính linh hoạt| Ít linh hoạt, bố cục tĩnh | Linh hoạt hơn, bố cục động  |
| Hiệu suất | Hiệu quả cao, cần yêu cầu nhiều tài nguyên hơn | Hiệu quả và tối ưu hóa hiệu suất |
| Tài liệu và cộng đồng | Tài liệu nhiều, cộng đồng lớn | Tài liệu ít hân cũng như công đồng chưa lớn |


![image](https://github.com/user-attachments/assets/e22945be-dba0-43b6-b995-463c63d6cb91)
![image](https://github.com/user-attachments/assets/a1dad7d3-2f07-4492-9323-ddf9adda60fa)



## Summary cách tạo view từ xml
LayoutInflate sẽ chuyển xml về tree structure để kotlin/java có thể hiểu được. Mỗi phần tử XML trở thành một đối tượng trong cây cấu trúc này, thông qua các bước sau:
1. Lấy LayoutInflater từ ngữ cảnh.
2. Sử dụng LayoutInflater để lấy tài nguyên XML dưới dạng XmlResourceParser.
3. Gọi phương thức inflate nội bộ để tạo đối tượng view từ đó.
4. Thêm view vào giao diện người dùng.
5. Lấy các thành phần con từ view để thực hiện các thao tác khác.
6. Đóng trình phân tích cú pháp XML sau khi sử dụng.

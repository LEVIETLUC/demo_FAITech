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


## XML với Component thuần trong kotlin
### Giống nhau
1. Đều dùng để xây dựng UI
2. Tuỳ chỉnh giao diện: đều cho phép tuỳ chỉnh, thay đổi giao diện
3. kết quả đầu ra: đều tại ra giao diện cuối cùng cho người dùng là như nhau
4. Cùng Android SDk: đều dùng các thành phần UI có sẵn trong SDK

### khác Nhau
| So sánh | XML | Component thuần trong kotlin |
| ------------- | ------------- | ------------- |
| Cú pháp, cách tiếp cận | Sử dụng XML để định nghĩa cấu trúc giao diện | Sử dụng Kotlin để mô tả các thành phần UI và cách chúng liên kết với nhau |
| Trực quan hóa UI | Trực quan hơn, có thể dùng kéo thả| Các thành phần định nghĩa hoàn toàn trong Kotlin, khó hình dung hơn nếu không bấm debug |
| Tính linh hoạt| Tách biệt rõ ràng giữa giao diện và logic ứng dụng nhưng cần thời gian để nạp XML vào | Tích hợp chặt chẽ với logic của ứng dụng, tạo các giao diện động một cách linh hoạt hơn |
| Hiệu suất| chậm hơn chút vì XML cần tốn thêm thời gian để nạp vào | có thể nhanh hơn một chút vì không cần nạp XML vào |

## RecyclerView
1. Thêm RecyclerView vào Layout XML.
2. Cần tạo các lớp Adapter để format dữ liệu và ViewHolder để gắn dữ liệu vào các widget đã được ánh xạ tương ứng bằng id.
<img width="960" alt="Screenshot 2024-08-05 at 14 59 23" src="https://github.com/user-attachments/assets/de01851e-613b-4955-aaad-261ecb46554e">

3. Khỏi tạo RecyclerView trong Activity thiết lập LayoutManager và gán Adapter vào.
<img width="761" alt="Screenshot 2024-08-05 at 15 00 27" src="https://github.com/user-attachments/assets/2753934b-db74-4209-85a3-f19a518abec1">



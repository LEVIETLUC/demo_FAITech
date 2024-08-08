# demo_FAITech

## Mục Lục
* [XML vs Jetpack Compose](#XML-vs-Jetpack-Compose)
* [Summary cách tạo view từ xml](#Summary-cách-tạo-view-từ-xml)
* [XML với Component thuần trong kotlin](#XML-với-Component-thuần-trong-kotlin)
* [RecyclerView](#RecyclerView)
* [ViewGroup](#ViewGroup)
* [onMeasure và onLayout](#onMeasure-và-onLayout)
* [LayoutParams](#LayoutParams)
     
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
### Các bước cơ bản khi sử dụng RecyclerView
1. Thêm RecyclerView vào Layout XML.
2. Cần tạo các lớp Adapter để format dữ liệu và ViewHolder để gắn dữ liệu vào các widget đã được ánh xạ tương ứng bằng id.
3. Khởi tạo RecyclerView trong Activity thiết lập LayoutManager và gán Adapter vào.

### Class FeatureAdapter
- Là một lớp kế thừa từ RecyclerView.Adapter để điểm trung gian format danh sách dữ liệu thành danh sách item
- Thông báo cho OnFeatureClickListener khi một item nào đó được click
- Cung cấp các phương thức: onCreateViewHolder, onBindViewHolder, getItemCount để RecyclerView hoạt động.
### Inner Class FeatureViewHolder
- Lưu trữ các view: Các biến featureName, featureDescription, editButton tham chiếu đến các view trong layout của một item (ví dụ: TextView, ImageButton).
- Xử lí sự kiện click của một item hoặt một image button, phương thức onFeatureClick của listener sẽ được gọi.
### onCreateViewHolder
- Tạo ra một đối tượng FeatureViewHolder mới cho mỗi dòng dữ liệu cần hiển thị
- Dùng inflate để tham chiếu tới R.layout.item_feat_option để chuẩn bị gán dữ liệu vào
### onBindViewHolder
- Được gọi khi RecyclerView cần gắn dữ liệu vào một view holder.
- Cập nhật nội dung các view trong view holder
### getItemCount
- Trả về tổng số item cần hiển thị trong RecyclerView.
### LinearLayoutManager
- Được dùng để sắp xếp cái item trong RecyclerView theo chiều dọc
### ItemDecoration
- Được dùng để thêm đường phân cách giữa các item và mỗi divider được them theo chiều dọc của danh sách
### onFeatureClick 
- Đây là hàm được gọi khi người dùng click vào item hoặc imageButton, hàm này được khai báo trong interface OnFeatureClickListener
- Hàm này dược dùng để điều hướng từ trang MainActivity đi tới những trang Feature khác
### Tổng kết
- RecyclerView là một container dùng để hiện thị danh sách với các item có thể linh hoạt chỉnh sửa
- Có thể thay thế RecyclerView bằng ListView và GridView. Tuy nhiên có thể không 2 loại đó không có thể chỉnh sửa linh hoạt bằng RecyclerView
#### Mục đích sử dụng:
- Hiển thị danh sách sản phẩm,
- Tạo các giao diện dạng lưới, danh sách ngang, danh sách dọc...
- Tùy chỉnh giao diện của các item.
#### Các thành phần cơ bản:
- Adapter: Liên kết dữ liệu với các view trong RecyclerView.
- LayoutManager: Xác định cách các item được bố trí trong RecyclerView.
- ViewHolder: Đại diện cho một item trong RecyclerView.
- ItemDecoration: Thêm phần trang trí cho các item.
- ItemAnimator: Tạo các hiệu ứng động khi thêm, xóa hoặc cập nhật item.
## ViewGroup
| So sánh | LinearLayout | RelativeLayout | ConstraintLayout | FrameLayout |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| Giống nhau | Dùng để chưa các view con, quản lý sắp xếp bố cục các view con | Dùng để chưa các view con, quản lý sắp xếp bố cục các view con | Dùng để chưa các view con, quản lý sắp xếp bố cục các view con | Dùng để chưa các view con, quản lý sắp xếp bố cục các view con | Dùng để chưa các view con, quản lý sắp xếp bố cục các view con |
| Khác nhau | Chỉ sắp xếp các view con theo chiều dọc hoặc ngang | Sắp xếp tương đối theo mối quan hệ của các view con | Sắp xếp theo sự ràng buộc | Sắp xếp theo kiểu chồng |
| Ưu Điểm | Dễ sử dụng, phù hợp với những bố cục đơn giản | Linh hoạt trong việc định vị vị trí tương đối | Linh hoạt, tối ưu tốt với nhiều view con phức tạp, nhiều tính năng hỗ trợ | Đơn giản, hỗ trợ tốt cho fragment |
| Nhược điểm | Khó tạo những bố cục phức tạp, thiếu linh hoạt | Phức tạp và khó bảo trì khi có nhiều view | Cú pháp phức tạp | Thiếu tính linh hoạt, không phù với layout phức tạp |

## onMeasure và onLayout
### onMeasure
- Xác định kích thước của ViewGroup và các View con.
- Với đầu vào là(widthMeasureSpec, heightMeasureSpec) và đầu ra là(kích thước của ViewGroup).
- Sử dụng measureChildWithMargins để đo kích thước của từng view con, bao gồm cả margin.
- Chọn maxWidth(chiều rộng lớn nhất của các view con), totalHeight(tổng chiều cao của các view con).
#### onLayout
- Đặt vị trí và kích thước cho các view con trong ViewGroup.
- Với đầu vào là(changed, l, t, r, b) và đầu ra là(các view con được bố trí trong ViewGroup).
- Căn chiều ngang của view bằng childLeft và chiều dọc của view bằng cách tính currentTop.
- Dùng child.layout để thiết lập vị trí của view dựa trên tính toán ở trên

## LayoutParams
- Mục đích: dùng để quản lý bố cục và kích thước các view con trong cùng 1 ViewGroup(do đó khi tạo 1 mỗi ViewGroup thì cần có 1 LayoutParams)

#### ViewGroup.LayoutParams:
- Lớp cơ sở của các loại LayoutParams
- chứa các thuộc tính cơ bản width, height.
- Dùng để xác định kích thước của layout
- MarginLayoutParams được kế thừa từ ViewGroup.LayoutParams và LinearLayout.LayoutParams, RelativeLayout.LayoutParams, FrameLayout được kế thừa từ MarginLayoutParams 

| So sánh | MarginLayoutParams | LinearLayout.LayoutParams | RelativeLayout.LayoutParams | FrameLayout |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| Đặc điểm | Thêm các thuộc tính margin: leftMargin, topMargin, rightMargin, và bottomMargin. | Thêm các thuộc tính weight, gravity | Thêm thuộc tính xác định mỗi quan hệ của các view con: alignParentTop, below, toRightOf, centerInParent | Thêm thuộc tính gravity |
| Sử dụng | Dùng để quản lý margin của các view con trong ViewGroup | Dùng để phân chia, sắp xếp các view con theo một hướng hay chiều nhất định trong ViewGroup | Dùng để xác định vị trí của view con tương đối so với vị trí của của các view con khác | Dùng để căn chỉnh các view con trong FrameLayout |

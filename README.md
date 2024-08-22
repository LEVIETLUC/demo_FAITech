# demo_FAITech

## Mục Lục
* [XML vs Jetpack Compose](#XML-vs-Jetpack-Compose)
* [Summary cách tạo view từ xml](#Summary-cách-tạo-view-từ-xml)
* [XML với Component thuần trong kotlin](#XML-với-Component-thuần-trong-kotlin)
* [RecyclerView](#RecyclerView)
* [ViewGroup](#ViewGroup)
* [onMeasure và onLayout](#onMeasure-và-onLayout)
* [LayoutParams](#LayoutParams)
* [BoringLayout, DynamicLayout, StaticLayout](#BoringLayout-DynamicLayout-StaticLayout)
* [Handle event button](#Handle-event-button)
* [Summary view](#Summary-view)
* [Xử lí TouchEvent](#Xử-lí-TouchEvent)
* [ScrollView](#ScrollView)
* [Service](#Service)
* [JSON](#JSON)
* [HTTP](#HTTP)
* [Render](#Render)

## XML vs Jetpack Compose

| So sánh | XML | Jetpack compose |
| ------------- | ------------- | ------------- |
| Mô hình lập trình | Imperative Programming | Declarative Programming  |
| Quản lý trạng thái | Quan tâm tới các bước thực hiện cụ thể | Chỉ quan tâm tới đầu vào và đầu ra của vấn đề |
| Cấu trúc | file XML cho phần giao diện, file java/kotlin cho phần handle event | viết tất cả bằng file kotlin  |
| Đọc, viết code | Phức tạp, khó khăn hơn | dễ dàng, ngắn gọn hơn  |
| Tính linh hoạt| Ít linh hoạt, bố cục tĩnh | Linh hoạt hơn, bố cục động  |
| Hiệu suất | Hiệu quả cao, cần yêu cầu nhiều tài nguyên hơn | Hiệu quả và tối ưu hóa hiệu suất |
| Tài liệu và cộng đồng | Tài liệu nhiều, cộng đồng lớn | Tài liệu ít hơn cũng như cộng đồng chưa lớn |


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

## BoringLayout, DynamicLayout, StaticLayout
Với TextView trong CustomView có thể thay thế BoringLayout bằng DynamicLayout, StaticLayout với điều kiện:
- Nếu đoạn text ó thể thay đổi sau khi layout đã được tạo thì sử dụng DynamicLayout
- Nếu đoạn text không thay đổi nhưng cần viết đoạn text dài và nhiều dòng thì sử dụng StaticLayout
- Còn đoạn text ngắn và không thay đổi thì nên sử dụng BoringLayout

| So sánh | BoringLayout                                                                                                                                         | StaticLayout                                                                                                                                                                                        | DynamicLayout                                                                                                                                                                                                   |
| ------------- |------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Đặc điểm | - Phù hợp cho sử dụng văn bản ngắn, đơn giản </br> - Không thể thay đổi động sau khi đã tạo layout </br> - Sử dụng tài nguyên ít nhất trong 3 layout | - Phù hợp với những đoạn văn bản dài, có nhiều dòng </br> - Không thể thay đổi động sau khi đã tạo layout </br> - Sử dụng ít tài nguyên hơn DynamicLayout vì Không cần phải duy trì trạng thái cập nhật | - Phù hợp với những đoạn văn bản cần cập nhật nội dung nhiều </br> - Có thể thay đổi động nội dung text sau khi đã tạo Layout </br> - Sử dụng nhiều tài nguyên vì cần duy trì trạng thái để xử thay đổi văn bản |

## Handle event button
- Để có thể xử click button trong Custom view có thể override phương thức onTouchEvent của View
- Kiểm tra xem điểm chạm có nằm trong vùng button không
- Nếu nằm trong vùng button thì xử lý sự kiện click button

## Summary view
1. Khởi Tạo View
- Tạo một đối tượng View thông qua các lớp như Activity, Fragment,... Các thuộc tính màu sắc, kích thước được thiết lập
2. Đo Kích Thước
- override hàm onMeasure với tham số đầu vào widthMeasureSpec và heightMeasureSpec để đo và thiết lập kích thước của view 
3. Bố trí view
- override hàm onLayout sau khi view đã được xác định, dùng LayoutParams để định kích thước và vị trí của nó
4. Thay đổi vị trí, kích thước
- hàm sẽ được gọi khi vị trí hay kích thước của view thay đổi(thường nào sau khi đo kích thước và trước khi vẽ lên giao diện)
5. Vẽ giao diện
- override hàm onDraw để vẽ nội dung view lên canvas
6. Xử Lý Sự Kiện
- Nhận diện và xử lý các sự kiện người dùng tương tác với giao diện bằng một số phương thức như onTouchEvent, onClick,...
7. Yêu cầu vẽ lại
- Cập nhật lại view khi có sự thay đổi, gọi phương thức invalidate để yêu cầu vẽ lại view

## Xử lí TouchEvent
- Với trường hợp khi ViewGroup A đang chứa View B thì:
  + khi ViewGroup A gọi onInterceptTouchEvent = true để chặn sự kiện chạm thì dispatchTouchEvent sẽ không phân phối tới View B mà sẽ giao cho TouchEvent của A xử lí sự kiện chạm đó
  <img width="805" alt="image" src="https://github.com/user-attachments/assets/844e165d-29df-4845-8ade-e0137c1ee7c9">

  + Khi ViewGroup A gọi onInterceptTouchEvent = false để không chặn sự kiện chạm thì dispatchTouchEvent sẽ phân phối tới View B và TouchEvent của view B sẽ xử lí sự kiện chạm trước và có thể request viewgroup những lần sau không chặn sự kiện
  <img width="845" alt="image" src="https://github.com/user-attachments/assets/49322aef-8f78-4abb-9f3e-fb23919e261c">
- Tóm lại, nếu onInterceptTouchEvent được gọi = true trước thì có chạm bao nhiêu lần thì sự kiện chạm cũng không xuống được tới view con cho duf view có con có gọi requestDisallowInterceptTouchEvent
- để một dispatchTouchEvent có thể truyền TouchEvent sang một View con khác khi onInterceptTouchEvent của ViewGroup = fasle 
  + dispatchTouchEvent kiểm tra từng view con để xem view nào có thể nhận sự kiện chạm tiếp theo bằng cách tính toán tọa độ của sự kiện so với vị trí của các view con khác
  + Sau khi xác định được view tiếp theo nhận sự kiện chạm thì tiến hành tạo bản sao của MotionEvent và sau đó gọi phương thức dispatchTouchEvent của view đã được xác định chuẩn bị nhận sự kiện để bắt đầu chuyển bản sao sự kiện chạm sang view đó
  + Cuối cùng giải phóng bộ nhớ và return true để đánh dấu sự kiện đã được xử lý
## ScrollView
- Dùng để có thể cuộn xem nội dung khi nội dung vượt quá kích thước màn hình
- Để sử dụng ScrollView cần bọc nội dung cần cuộn vào ScrollView
- Xử lý 2 ScrollView lồng nhau: 
  + sử dụng canScrollVertically để ScrollView con đã cuộn đến đỉnh hay cuối cùng chưa
  + Nếu ScrollView đã cuộn tới hay cuối cùng thì dùng requestDisallowInterceptTouchEvent để bỏ chặn sự kiện cuộn của ScrollView cha
  + Nếu ScrollView chưa cuộn tới hay cuối cùng thì chặn sự kiện cuộn của ScrollView cha

## Service
- Service là một thành phần của android thực hiện chạy các tác vụ nền mà không cần giao diện người dùng
- Unbound Service: Không cần ràng buộc với bất kỳ thành phần nào, chạy liên tục cho đến khi hoàn thành công việc hoặc bị buộc huỷ
- Bound Service: Cung cấp các ràng buộc liên kết cho các thành phần khác, như Activity, Broadcast Receiver, Service khác
- Có 3 loại service cụ thể: Foreground Service(Unbound Service), Background Service(Unbound Service), Bound Service
### Vòng đời của Service
 - onCreate(): Được gọi khi Service được tạo lần đầu tiên.
 - onStartCommand(): Được gọi mỗi khi một component yêu cầu Service bắt đầu, cung cấp các chỉ dẫn từ Intent.
 - onBind()(dành cho bound service): Được gọi khi một component muốn liên kết với Service.(với Unbound Service thì thường sẽ return null)
 - onUnbind()(dành cho bound service: Được gọi khi tất cả các client đều ngừng liên kết với Service.
 - onDestroy(): Được gọi khi Service bị dừng hoặc hủy.

### Phân biệt Foreground service và Background service và Bound service
- Foreground service: 
  + Là service chạy trong nền nhưng người dùng có thể nhận biết được nó đang chạy thông qua notification và vẫn sẽ tiếp tục chạy ngay cả khi không activity trên màn hình
  + Thường được sử dụng khi cần chạy các tác vụ lâu dài như phát nhạc, bấm thời gian, định vị GPS
- Background service:
    + Là service chạy trong nền nhưng người dùng không nhận biết được nó đang chạy, cũng không tuơng tác với nó
    + Thường được sử dụng khi cần chạy các tác vụ ngắn gọn như đồng bộ hóa dữ liệu, tải tài nguyên từ mạng,
- Bound service:
    + Bound Service cho phép các thành phần khác (như Activity, Fragment) liên kết (bind) với nó để giao tiếp và sử dụng các phương thức hoặc truy xuất dữ liệu từ Service.
    + Thường được sử dụng khi cần giao tiếp giữa các thành phần khác như Activity, Fragment, Service khác
### JSON
- Là dạng ngôn ngữ chứa dữ liệu. Dữ liệu được lưu dưới dạng cặp key-value
- Được xây dựng ở dạng data hierarchy
- Cấu trúc của JSON: 
  + Object: là một tập hợp các cặp key-value, mỗi cặp key-value được phân cách bởi dấu phẩy và được bao bọc bởi dấu ngoặc nhọn
  + Array: là một tập hợp các giá trị, mỗi giá trị được phân cách bởi dấu phẩy và được bao bọc bởi dấu ngoặc vuông
  + Value: là một giá trị, có thể là một chuỗi, một số, một boolean, một object hoặc một array
 - Quá trình chuyển đổi JSON sang Object:
    + Parsing dữ liệu: pasing sẽ đọc dữ liệu từng ký tự. Xác đinh các ký tự đặc biệt giữa key-value,”:”, “;”, “,”, “{“, “}”, “[“, “]”
    + Xác định kiểu dữ liệu: kiểm tra dữ liệu đọc được từ JSON để xác định kiểu dữ liệu của dữ liệu đó
    + Thư viện sẽ xây dựng các đối tượng tương ứng trong bộ nhớ, một cặp "key-value" sẽ được chuyển thành một trường trong JsonObject

### HTTP
- Là giao thức truyền tải dữ liệu giữa máy chủ và máy client
- Http hoạt động theo mô hình request-response, client gửi request đến server và server trả về response với nội dung tương ứng
- Các phương thức HTTP thường dùng:
  + GET: lấy dữ liệu từ server
  + POST: gửi dữ liệu lên server
  + PUT: cập nhật dữ liệu lên server
  + DELETE: xóa dữ liệu trên server
- ResponseCode tương tự như cờ hiệu thể hiện trạng thái mà một máy chủ gửi về để đáp lại yêu cầu HTTP từ máy khách. Nó cho biết kết quả của việc xử lý yêu cầu. Một số đầu ResponseCode
<img width="153" alt="image" src="https://github.com/user-attachments/assets/5084713a-50d4-4d24-a1cf-3e8935655773">

#### Luồng hoạt động của HTTPConnection
- gán một url sau đó thực hiện openConnection để thiết lập kết nối với server bằng url
- Đặt nó trong khối lệnh try catch để có thề bắt lỗi
- Khi mở connect thành công thì requestMethod cuối cùng sẽ kết nối với url đó và method đó
- Bắt đầu truyền request dữ liệu qua cho server
- Và đợi Nhận response và duyệt responseCode và dựa vào code đó thì sẽ xác định làm gì tiếp theo
- ở finally thì cần có closeConnection để tránh làm tiêu tốn tài nguyên


#### Đóng/Mở connection
  * openConnection(): phương thức này được dùng để thiết lập kết nối đến một URL thông qua một đối tượng HttpURLConnection. Đây là bước đầu tiên trong việc giao tiếp với một máy chủ qua HTTP. openConnection() tạo ra một kết nối và chuẩn bị để gửi yêu cầu, nhưng chưa thực hiện kết nối vật lý đến máy chủ
  * connect(): Phương thức này thực sự mở kết nối TCP/IP đến máy chủ từ phía máy khách. Nó xảy ra sau khi bạn đã cấu hình yêu cầu HTTP (như phương thức GET hoặc POST, phần header, phần body) và gửi dữ liệu đi. connect() thực hiện kết nối ở Transport Layer.
  * disconnect(): Phương thức này được sử dụng để đóng kết nối HTTP sau khi hoàn tất việc trao đổi dữ liệu. Nó giải phóng tài nguyên liên quan đến kết nối đó. Việc đóng kết nối này diễn ra ở Application Layer, nhưng nó cũng tác động đến Transport Layer để ngắt kết nối TCP vì connect được thiết lập ở Transport Layer.

#### InputStream/OutputStream
- Đều là phương thức để đọc/ghi từng byte
- InputStream đọc các byte
  - đọc từng byte dữ liệu một cách tuần tự từ đầu đến cuối file
  - Cần chuyển đổi byte sang kiểu dữ liệu khác để đọc file
  - Mỗi lần gọi read(): Phương thức này sẽ đọc một byte từ file và trả về giá trị int của byte đó
  - Khi kết thúc file: Khi không còn dữ liệu nào để đọc (tức là đã đến cuối file), phương thức read() sẽ trả về giá trị -1. Đây là cách để biết rằng bạn đã đọc hết dữ liệu trong file.
  - Luôn luôn đóng luồn đọc sau khi xử lí xong đọc file để giải phóng bộ nhớ, tài nguyên
- OutputStream ghi các byte
  - ghi từng byte dữ liệu một cách tuần tự từ đầu đến cuối file
  - Cần chuyển đổi những kiểu dữ liệu khác sang byte để ghi vào file
  - Mỗi lần gọi write(): Phương thức này sẽ ghi một byte vào file. Để ghi một byte, thì cần truyền một giá trị int vào phương thức write(). Giá trị int này sẽ được chuyển đổi thành byte và ghi vào file.
  - Khi kết thúc file: Khi bạn đã ghi hết dữ liệu vào file, bạn cần gọi phương thức close() để đóng luồng ghi. Điều này sẽ giải phóng tài nguyên và đảm bảo rằng dữ liệu đã được ghi vào file.

- Ngoài ra còn BufferedInputStream và BufferedOutputStream
  - 2 phương thức là lớp con của InputStream và OutputStream
  - Thì 2 lớp trên khác với InputStream và OutputStream ở chỗ là BufferedInputStream và BufferedOutputStream sử dụng bộ đệm để đọc và ghi dữ liệu từ file
  - Nghĩa là với một dữ liệu từ file nó chuyển một lượng lớn dữ liệu vào bộ (thường kích thước bộ đệm là 8KB) và sau đó đọc từ bộ đệm đó thay vì đọc từ file trực tiếp
  - Tương tục với đọc, ghi dữ liệu vào file thì nó sẽ ghi vào bộ đệm trước rồi mới ghi vào file

#### Blocking/Non-blocking
- Blocking: là luồng hay app phải dừng lại để đợi cho một cv nào đó được thực thi xong rồi mới được thực hiện tiếp
  - Gây ra một số vấn đề như: thời gian chờ đợi hoàn thành toàn bộ công việc lâu ảnh hưởng perform máy, sẽ có một số task sẽ không liên quan gì tới tác hiện tại đang chạy những vẫn bị block, phân bố xử lí không phù hợp
- Non-blocking: là luồng hay app hiện tại sẽ không bị chặn khi có một tác đang thực hiện, điều này giúp có thể xử lý thêm các tác khác trong lúc chờ tác kia chạy xong
  - có một số cách để xử lý như : dùng nhiều thread phụ khác trong cùng một process để k làm ảnh hưởng tới thread chính, thậm chí có thể dùng nhiêu process khác nhau hoặc là đơn giản hơn một thread có thể chạy nhiều coroutine thì cũng được xem là mô hình non-blocking

#### Handshake
- Là quá trình thiết lập kết nối giữa hai hệ thống hoặc 2 thiết bị trước khi trao đổi dữ liệu. Để đảm bảo rằng 2 bên đều đồng ý và sẵn sàng nhận dữ liệu
- Mục đích chính của Handshake:
  * Đồng bộ hóa: Đảm bảo cả hai bên hiểu và đồng ý về cách thức giao tiếp.
  * Xác thực: Xác nhận danh tính của các bên tham gia kết nối (trong một số trường hợp).
  * Thiết lập thông số: Thỏa thuận về các tham số kết nối như kích thước gói tin, phương thức mã hóa,...
  * Khởi tạo kết nối an toàn: Đặc biệt quan trọng trong các giao thức bảo mật như TLS/SSL.
- Thông thuông sẽ gồm có: Three-way handshake, Four-way handshake, SSL handshake
  - Three-way handshake:
    + Client gửi một gói tin SYN đến server để bắt đầu kết nối
    + Server gửi một gói tin SYN-ACK để xác nhận và yêu cầu client xác nhận
    + Client gửi một gói tin ACK để xác nhận và bắt đầu truyền dữ liệu
  - Four-way handshake:
    + Client gửi một gói tin FIN để kết thúc kết nối
    + Server gửi một gói tin ACK để xác nhận
    + Sau đó Server gửi tiếp một gói tin FIN để kết thúc kết nối
    + Client gửi một gói tin ACK để xác nhận và đóng kết nối
  - SSL handshake: 
    + Client gửi một gói tin để yêu cầu kết nối an toàn
    + Server phản hồi lại với server random và SSL certificate
    + Client xác minh SSL certificate và tạo một pre-master secret
    + Client và server sử dụng pre-master secret để tạo ra master secret và session key
    + Client và server sử dụng session key để mã hóa và giải mã dữ liệu
    + Cuối cùng client gửi một gói tin kết thúc để xác nhận kết nối an toàn
    + Server phản hồi lại với một gói tin kết thúc để xác nhận kết nối an toàn
    + Kết nối an toàn được thiết lập và dữ liệu được truyền tải an toàn

#### Certificates
- đóng vai trò quan trọng trong việc xác thực và mã hóa trong các kết nối an toàn.
  - vơi 2 mục đích chính là xác thực và mã hóa
    + Xác thực: giúp xác thực danh tính của một website hoặc một server.
    + Mã hóa: Chứng chỉ số chứa khóa công khai (public key) được sử dụng để mã hóa dữ liệu trong quá trình handshake TLS.
  - Cấu trúc của một certifiate:
    + Subject: Thông thường là tên miền của máy chủ.
    + Issuer: Thông tin về CA đã cấp chứng chỉ.
    + Validity: Thời gian hiệu lực của chứng chỉ (từ ngày bắt đầu đến ngày kết thúc).
    + Public Key: Khóa công khai của máy chủ.
    + Signature Algorithm: Thuật toán được sử dụng để tạo chữ ký số của CA.
    + Digital Signature: Chữ ký số của CA, được tạo bằng cách mã hóa một hash của chứng chỉ bằng khóa riêng của CA.
  - Cách thức hoạt động:
    + Khi client kết nối đến server, server sẽ gửi chứng chỉ số của nó cho client.
    + Client kiểm tra tính hợp lệ của chứng chỉ.
    + Nếu chứng chỉ hợp lệ, client sẽ tiếp tục thiết lập kết nối an toàn. Nếu không, kết nối sẽ bị từ chối hoặc hiển thị cảnh báo cho người dùng.

### Render
Render giao diện là quá trình tạo và hiển thị khung hình từ ứng dụng lên màn hình. Để đảm bảo trải nghiệm người dùng mượt mà, ứng dụng phải render khung hình dưới 16ms để đạt 60 khung hình/giây (fps). Nếu ứng dụng vượt quá thời gian này, Choreographer sẽ bỏ qua khung hình đó(nghĩa là nếu khung hình render quá chậm thì thì Choreographer bỏ qua đoạn chậm đó để đi qua khung tiếp theo để đảm bảo kịp thời điểm cho khung hình tiếp theo) , dẫn đến hiện tượng giật (jank).
#### Một số cách để theo dõi, phát hiện Jank:
- **Visual inspection**: Kiểm tra các trường hợp sử dụng có jank trong giao diện bằng cách mở ứng dụng và thao tác qua các phần khác nhau, kèm theo đó là bật tính năng Profile GPU Rendering để thấy thời gian render của từng khung hình.
- **Systrace**: Công cụ này cung cấp chi tiết về hoạt động của thiết bị và giúp phát hiện khung hình chậm. Systrace phân tích các quy trình và luồng, hiển thị màu sắc để nhận biết khung hình bị jank.
- **Custom performance monitoring**: Khi không thể tái tạo jank trên thiết bị cục bộ, có thể sử dụng FrameMetricsAggregator và Firebase Performance Monitoring để theo dõi thời gian render.


#### Slow frames vs Frozen frames vs ARNS
| So sánh        | Slow frames                                                                                          | Frozen frames                                                                             | ANRS                                                                                                                           |
|----------------|------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| Rendering time | từ 16ms đến 700ms                                                                                    | từ 700ms đến 5s                                                                           | trên 5s                                                                                                                        |
| Visible user impact area | - Khi một RecyclerView cuộn một cách đột ngột</br> - giao diện có hoạt ảnh hoạt động không đúng cách | -  Trong quá trình khởi động app</br> - Hay khi chuyển từ màn hình này sang màn hình khác | - Là khi hoạt sự kiện chạm hay bấm nút không được phản hồi trong vòng 5s ngay cả khi không có hoạt động nào khác chạy trước đó |

#### Một số nguồn gây ra Jank phổ biến là

* **Danh sách cuộn (RecyclerView, ListView)**: Gây ra jank khi thực hiện những thao tác nặng trên UI thread.
* **RecyclerViews lồng nhau**: Tối ưu hóa bằng cách chia sẻ RecyclerView.RecycledViewPool giữa các RecyclerView con.
* **RecyclerView**: Too much inflation or Create is taking too long:
  - Inflation: Đây là quá trình tạo ra các đối tượng View từ file XML. Nếu quá trình này diễn ra quá nhiều hoặc mất quá nhiều thời gian, nó có thể gây ra jank.
  - Create is taking too long: Việc khởi tạo View Holder hoặc View mới mất nhiều thời gian, dẫn đến việc giao diện không mượt mà.
* **RecyclerView Bind taking too long**: Đây là quá trình gắn dữ liệu vào View khi RecyclerView cần hiển thị một item. Nếu quá trình này mất nhiều thời gian, nó sẽ gây ra jank khi người dùng cuộn.
* **RecyclerView or ListView**: Layout or draw taking too long:
  - Layout: Quá trình tính toán kích thước và vị trí của các View trong một bố cục. Nếu layout mất quá nhiều thời gian, nó có thể gây Jank.
  - Draw: Đây là quá trình vẽ các View lên màn hình. Nếu quá trình này kéo dài, nó sẽ gây ra hiện tượng jank

* **ListView Inflation**: Tương tự như trong RecyclerView, đây là quá trình tạo ra các View từ file XML. Nếu việc này diễn ra quá nhiều hoặc quá chậm trong ListView, nó sẽ dẫn đến jank.
* **Layout performance**: Đây là hiệu suất của quá trình bố cục (layout) của giao diện. Nếu việc layout diễn ra chậm, nó sẽ ảnh hưởng đến toàn bộ hiệu suất của giao diện và gây ra jan
* **Rendering performance**: Đây là hiệu suất của quá trình vẽ giao diện lên màn hình. Nếu quá trình này không diễn ra mượt mà, nó sẽ gây ra jank và ảnh hưởng đến trải nghiệm người dùng.
* **Rendering performance**: UI Thread: Đây là luồng chính chịu trách nhiệm quản lý và vẽ giao diện người dùng. Nếu UI Thread bị chặn hoặc hoạt động không hiệu quả, nó sẽ gây ra jank.
* **Rendering performance**: RenderThread: Một luồng riêng biệt xử lý việc vẽ các khung hình của giao diện. Nếu RenderThread gặp vấn đề về hiệu suất, nó sẽ gây ra hiện tượng jank.
* **Thread scheduling delays**: Các vấn đề liên quan đến việc sắp xếp và thực thi các luồng (threads). Nếu có sự chậm trễ trong việc thực thi các luồng cần thiết, nó sẽ gây ra jank.
* **Object allocation and garbage collection**
  - Object allocation: Quá trình phân bổ bộ nhớ cho các đối tượng mới. Nếu việc phân bổ quá nhiều hoặc diễn ra không hiệu quả, nó sẽ gây ra jank.
  - Garbage collection: Quá trình thu hồi bộ nhớ không sử dụng. Nếu quá trình này diễn ra quá thường xuyên hoặc mất nhiều thời gian, nó sẽ gây ra jank do việc tạm dừng các hoạt động khác

#### Một số cách giảm thiểu Jank
1. Tối ưu hóa Inflation
  - Sử dụng ViewHolder Pattern: Đảm bảo bạn đang sử dụng ViewHolder pattern để giảm thiểu số lần gọi findViewById(). Điều này sẽ giúp bạn tránh việc inflate lại các view không cần thiết.
  - Sử dụng LayoutInflater: Inflate layout chỉ một lần và sử dụng lại chúng bằng cách lưu trữ vào ViewHolder.
  - Sử dụng getViewTypeCount() và getItemViewType(): Nếu có các kiểu item khác nhau trong RecyclerView hoặc ListView, hãy sử dụng các phương thức này để tái sử dụng view và giảm thiểu quá trình inflation.
2. Tối ưu hóa Data Binding
- Giảm tác vụ nặng trong onBindViewHolder() hoặc getView(): Tránh thực hiện các tác vụ nặng như tính toán phức tạp hay truy cập mạng trong các phương thức này. Chuyển những tác vụ đó ra ngoài và sử dụng dữ liệu đã được chuẩn bị trước.
- Tránh tạo đối tượng mới trong onBindViewHolder() hoặc getView(): Thay vì tạo mới, cố gắng tái sử dụng các đối tượng đã có.
3. Tối ưu hóa Layout Performance
- Giảm độ phức tạp của layout: Giảm số lượng view con trong một layout, sử dụng ConstraintLayout hoặc FrameLayout để tăng hiệu suất layout.
- Kiểm tra layout hierarchy: Sử dụng công cụ layout inspector để theo dõi và tối ưu hóa cấu trúc layout.
4. Tối ưu hóa Rendering Performance
- Giảm số lần lặp vẽ: Tránh việc vẽ lại view không cần thiết. Sử dụng setHasTransientState(true) cho các view tạm thời không thay đổi.
- Sử dụng chế độ vẽ offscreen (đối với đối tượng lớn): Sử dụng canvas để vẽ những đối tượng lớn trước, nếu có thể, để làm giảm độ tốn tài nguyên quá mức khi phải render nhiều lần.
5. Tối ưu hóa Object Allocation & Garbage Collection
- Kiểm soát việc phân bổ đối tượng: Tránh tạo ra quá nhiều đối tượng tạm thời, như trong các vòng lặp hoặc xử lý dữ liệu lớn. Có thể sử dụng Object Pooling cho những đối tượng được sử dụng nhiều lần.
- Giảm thiểu việc sử dụng các đối tượng không cần thiết: Sử dụng các kiểu dữ liệu nguyên thủy thay vì đối tượng khi có thể.
- Hạn chế sử dụng transparency ở mức độ cần thiết, và tối ưu hóa các lớp overlay để giảm tải cho hệ thống.
- Giám sát và phân tích garbage collection: Sử dụng các công cụ như Android Profiler để theo dõi hoạt động garbage collection và việc phân bổ bộ nhớ.
6. Tối ưu hóa UI Thread và RenderThread
- Tránh thực hiện tác vụ nặng trên UI Thread: Chuyển các tác vụ nặng sang các thread khác hoặc sử dụng AsyncTask hoặc RxJava.
- Quản lý Thread schedule: Sử dụng các thư viện như Kotlin Coroutines hoặc Java ExecutorService để dễ dàng quản lý và tổ chức các tác vụ trên các thread.
7. Tối ưu hoá pin tiêu thụ
- Sử dụng các công cụ như Battery Historian để theo dõi và phân tích việc tiêu thụ pin của ứng dụng.
- Dùng JobScheduler để lên lịch các tác vụ nặng vào thời điểm thích hợp(như xử lí các tác vụ nặng khi thiết bị đang cắm sạc), giảm thiểu tiêu thụ pin.
8. Sử dụng Paging
- Tối ưu hóa quá trình tải dữ liệu: Khi làm việc với tập dữ liệu lớn, hãy sử dụng Paging Library để tải dữ liệu theo trang và chỉ tải những dữ liệu cần thiết.

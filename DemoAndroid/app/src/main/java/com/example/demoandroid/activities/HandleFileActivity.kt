package com.example.demoandroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.demoandroid.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*

class HandleFileActivity : AppCompatActivity() {

    // Khai báo các biến
    private lateinit var cutFileSequentialBtn : Button
    private lateinit var cutFileNonSequentialBtn : Button
    private lateinit var dataFromFileTxt: TextView
    private lateinit var pathFileOutputTxt: TextView
    private val filePath = "text.txt"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handle_file)

        // Khởi tạo các view
        cutFileSequentialBtn = findViewById(R.id.button_sequential)
        cutFileNonSequentialBtn = findViewById(R.id.button_non_sequential)
        dataFromFileTxt = findViewById(R.id.log_text_view)
        pathFileOutputTxt = findViewById(R.id.path_text_view)
        displayFileContent()

        cutFileSequentialBtn.setOnClickListener {
            cutFileSequential()
            // Hiển thị đường dẫn tuyệt đối của file output
            pathFileOutputTxt.text = "Path file output sequential : ${filesDir.absolutePath}"
        }

        cutFileNonSequentialBtn.setOnClickListener {
            cutFileNonSequential()
            // Hiển thị đường dẫn tuyệt đối của file output
            pathFileOutputTxt.text = "Path file output non-sequential : ${filesDir.absolutePath}"
        }


    }
    /**
    * Với hàm displayFileContent được sử để đọc nội dung của file text.txt từ thư mục và hiển thị lên
    * Input: string filePath
    * Output mong muốn: Hiển thị nội dung file text.txt lên TextView
    * Cách impl hàm:
    * - Tạo CoroutineScope để chạy 1 coroutine ở IO thread để tối ưu hoá việc đọc file và ngăn không block main thread
    * - Sử dụng try catch để bắt lỗi khi đọc file
    * - Mở file text.txt từ thư mục assets và được mở dưới dạng inputStream
    * - Sau khi mở tệp tin, InputStreamReader được sử dụng để chuyển đổi InputStream sang Reader. Sau đó, BufferedReader được sử dụng để đọc nội dung tệp tin này
    * - BufferedReader.readLines() sẽ đọc toàn bộ tệp tin và trả về danh sách các dòng văn bản (mỗi phần tử trong danh sách là một dòng của tệp)
    * - Sử dụng withContext(Dispatchers.Main) để chuyển sang main thread và để cập nhật nội dung file lên TextView
    */
    private fun displayFileContent(){

        CoroutineScope(Dispatchers.IO).launch {
            try {
                assets.open(filePath).use { inputStream ->
                    BufferedReader(InputStreamReader(inputStream)).use { reader ->
                        val lines = reader.readLines()
                        withContext(Dispatchers.Main) {
                            dataFromFileTxt.text = lines.joinToString("\n")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Với hàm cutFileSequential được sử dụng để cắt file text.txt thành 10 file con theo cách tuần tự và các lần chi được thực hiện bởi 10 coroutine
     * Input: string filePath
     * Output mong muốn: Cắt file text.txt thành 10 file con theo cách tuần tự và lưu vào thư mục files
     * Cách impl hàm:
     * - Hàm bắt đầu bằng việc gọi hàm copyAssetToCache(filePath) để sao chép tệp tin từ thư mục assets vào thư mục cache nội bộ của ứng dụng
     * - Kiểm tra tệp tin tempFile sau khi sao chép xong có tồn tại không, nếu không tồn tại thì trả về null, ngược lại thì tiếp tục thực hiện
     * - sử dụng một vòng lặp for (i in 0 until 10) để tạo ra 10 tệp đầu ra,
     * - Định nghĩa tên tệp đầu ra outputFileName = "output_sequential_${i+1}.txt"
     * - Tạo CoroutineScope để chạy 1 coroutine ở IO thread để tối ưu hoá việc đọc và ghi file và ngăn không block main thread
     * - Trong coroutine, gọi hàm readAndWriteFileSequentially(file, i * 10, outputFileName) để cắt file và ghi file
     * - Với cái tham số truyền vào là file để đọc, i * 10 để bắt đầu từ dòng thứ i * 10 và outputFileName là tên file tương ứng
    */
    private fun cutFileSequential() {
        val tempFile = copyAssetToCache(filePath)
        tempFile?.let { file ->
        for (i in 0 until 10) {
            val outputFileName = "output_sequential_${i+1}.txt"
            CoroutineScope(Dispatchers.IO).launch {
                    readAndWriteFileSequentially(file, i * 10, outputFileName)
                }
        }
        }
    }


    /**
     * - Tương tự như cách impl hàm cutFileSequential, hàm cutFileNonSequential được sử dụng để cắt file text.txt thành 10 file con theo cách không tuần tự và các lần cắt được thực hiện bởi 10 coroutine
     */

    private fun cutFileNonSequential() {
        val tempFile = copyAssetToCache(filePath)
        tempFile?.let { file ->
        for (i in 0 until 10) {
            val outputFileName = "output_non_sequential_${i+1}.txt"
            CoroutineScope(Dispatchers.IO).launch {
                    readAndWriteFileNonSequentially(file, i, outputFileName)
                }

            }
        }
    }

    /**
     * - Hàm copyAssetToCache được sử dụng để sao chép tệp tin từ thư mục assets vào thư mục cache nội bộ của ứng dụng
     * - Vì hàm RandomAccessFile không thể đọc file từ thư mục assets nên cần sao chép file từ thư mục assets vào thư mục cache
     * - Input: string assetFileName
     * - Output mong muốn: File? (trả về file sau khi sao chép hoặc null nếu có lỗi)
     * - Cách impl hàm:
     * - Sử dụng try catch để bắt lỗi khi sao chép file
     * - Hàm bắt đầu bằng việc tạo một đối tượng File mới trong thư mục cache của ứng dụng, sử dụng tên tệp tin được truyền vào từ assetFileName
     * - Mở file text.txt từ thư mục assets và được mở dưới dạng inputStream
     * - FileOutputStream được sử dụng để ghi dữ liệu từ inputStream vào tệp tin mới được tạo
     * - Một buffer được tạo ra với kích thước 1024 byte, dữ liệu từ inputStream được đọc vào buffer và ghi vào tệp tin mới
     * - vòng lặp while được sử dụng để đọc từng đoạn dữ liệu từ InputStream và ghi vào OutputStream
     * - inputStream.read(buffer).also { length = it } > 0: Đọc dữ liệu từ InputStream vào buffer và lưu trữ số lượng byte đọc được vào length. Vòng lặp tiếp tục cho đến khi không còn dữ liệu nào để đọc.
     * - outputStream.write(buffer, 0, length): Ghi dữ liệu từ buffer vào tệp tin tạm thời với số byte được đọc trong mỗi vòng lặp
     * - Trả về tệp tin tạm thời sau khi sao chép xong
     */
    private fun copyAssetToCache(assetFileName: String): File? {
        return try {
            val tempFile = File(cacheDir, assetFileName)
            assets.open(assetFileName).use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (inputStream.read(buffer).also { length = it } > 0) {
                        outputStream.write(buffer, 0, length)
                    }
                }
            }
            tempFile
        } catch (e: Exception) {
            Log.e("FileCopyError", "Error copying asset file", e)
            null
        }
    }


    /**
     * - Hàm readAndWriteFileSequentially được sử dụng để đọc 10 dòng liên tiếp bắt đầu từ 1 dòng nhất định và ghi file theo cách tuần tự
     * - Input: File file, Int startLine, String outputFileName
     * - Output mong muốn: Ghi 10 dòng liên tiếp bắt đầu từ 1 dòng nhất định vào file output
     * - Cách impl hàm:
     * - Sử dụng RandomAccessFile để đọc file theo cách tuần tự
     * - Sử dụng StringBuilder để lưu trữ các dòng dữ liệu mà hàm sẽ đọc từ tệp tin
     * - Sử dụng hàm raf.seek(0) để đảm bảo rằng con trỏ của tệp tin đang ở vị trí bắt đầu
     * - Sử dụng vòng lặp while để đọc từng dòng của tệp tin cho đến khi đến dòng bắt đầu được chỉ định
     * - Sử dụng bytesRead để theo dõi số byte đã đọc từ đầu đến dòng startLine
     * - Để bỏ qua các dòng không cần thiết, hàm sử dụng raf.seek(bytesRead) để đặt con trỏ tệp tin về vị trí của dòng startLine
     * - Sử dụng vòng lặp for để đọc 10 dòng tiếp theo từ vị trí startLine trong tệp, đọc bằng raf.readLine() và được thêm vào buffer nếu dòng đó khác null
     * - Sau khi đã đọc đủ 10 dòng, gọi writeFile(outputFileName, buffer.toString()) để ghi các dòng đã đọc vào một tệp tin mới với tên outputFileName.
     */
    private fun readAndWriteFileSequentially(file: File, startLine: Int, outputFileName: String) {
        RandomAccessFile(file, "r").use { raf ->
            val buffer = StringBuilder()
            raf.seek(0)
            var currentLine = 0
            var bytesRead = 0L

            while (currentLine < startLine) {
                val line = raf.readLine()
                bytesRead += line.toByteArray().size + System.lineSeparator().toByteArray().size
                currentLine++
            }
            raf.seek(bytesRead)

            for (i in 0 until 10) {
                val line = raf.readLine()
                if (line != null) {
                    buffer.appendLine(line)
                } else {
                    break
                }
            }
            writeFile(outputFileName, buffer.toString())
        }
    }


    /**
     * - Hàm readAndWriteFileNonSequentially được sử dụng để đọc 10 dòng không liên tiếp(0,10,20...| 1,11,21... |2,12,22 ...) bắt đầu từ 1 dòng nhất định và ghi file theo cách không tuần tự
     * - Input: File file, Int startOffset, String outputFileName
     * - Output mong muốn: Ghi 10 dòng không liên tiếp bắt đầu từ 1 dòng nhất định vào file output
     * - Cách impl hàm:
     * - Sử dụng RandomAccessFile để đọc file theo cách không tuần tự
     * - Sử dụng StringBuilder để lưu trữ các dòng dữ liệu mà hàm sẽ đọc từ tệp tin
     * - Sử dụng một danh sách để lưu trữ vị trí của các dòng cần đọc
     * - Sử dụng vòng lặp while(true) với điều kiện dừng là khi raf.readLine() ?: break khi trả về null sẽ break vòng lặp
     * - Sử dụng raf.filePointer để lấy vị trí hiện tại của con trỏ tệp tin và sẽ được lưu vào danh sách lines
     * - nếu như thoả điều kiện (currentLine - startOffset) % 10 == 0 và currentLine >= startOffset sau đó tăng currentLine để kiểm tra dòng tiếp theo
     * - Sau khi xác định được tất cả các dòng cần đọc thì sử dụng forEach để duyệt qua những dòng được chọn trong
     * - Sủ dụng raf.seek(pos) để nhảy tới dòng được chọn và đọc dòng đó bằng raf.readLine() và thêm vào buffer
     * - Sau khi đã đọc xong, gọi writeFile(outputFileName, buffer.toString()) để ghi các dòng đã đọc vào một tệp tin mới với tên outputFileName.
     */
    private fun readAndWriteFileNonSequentially(file: File, startOffset: Int, outputFileName: String) {
        RandomAccessFile(file, "r").use { raf ->
            val buffer = StringBuilder()
            val lines = mutableListOf<Long>()
            var currentLine = 0

            while (true) {
                val lineStartPos = raf.filePointer
                val line = raf.readLine() ?: break
                if ((currentLine - startOffset) % 10 == 0 && currentLine >= startOffset) {
                    lines.add(lineStartPos)
                }
                currentLine++
            }

            lines.forEach { pos ->
                raf.seek(pos)
                buffer.appendLine(raf.readLine())
            }

            writeFile(outputFileName, buffer.toString())
        }
    }

    /**
     * - Hàm writeFile được sử dụng để ghi nội dung vào file và log ra thông báo đường dẫn của file
     * - Input: String outputFileName, String content
     * - Output mong muốn: Ghi nội dung vào file và log ra thông báo đường dẫn của file
     * - Cách impl hàm:
     * - Đầu tiên tạo một đối tượng File mới trong thư mục files của ứng dụng, sử dụng tên tệp tin được truyền vào từ outputFileName
     * - Mở một luồng ghi (FileOutputStream) tới tệp tin outputFile đã được tạo và bọc nó trong một BufferedOutputStream để tối ưu việc ghi dữ liệu
     * - Giúp giảm số lần truy cập bằng cách ghi dữ liệu vào bộ đệm trước khi ghi vào tệp
     * - Chuyển đổi chuỗi nội dung thành một mảng byte bằng toByteArray() và ghi vào tệp tin
     * - Gọi bos.flush() để đảm bảo rằng tất cả dữ liệu đã được ghi vào tệp tin
     * - Log ra thông báo "File $outputFileName written successfully ${outputFile.absolutePath}." về đường dẫn của file
     */
    private fun writeFile(outputFileName: String, content: String) {
        val outputFile = File(filesDir, outputFileName)
        BufferedOutputStream(FileOutputStream(outputFile)).use { bos ->
            bos.write(content.toByteArray())
            bos.flush()
        }
        Log.d("FileWritten", "File $outputFileName written successfully ${outputFile.absolutePath}.")
    }


    

}
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

    private lateinit var cutFileSequentialBtn : Button
    private lateinit var cutFileNonSequentialBtn : Button
    private lateinit var dataFromFileTxt: TextView
    private lateinit var pathFileOutputTxt: TextView
    private val filePath = "text.txt"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handle_file)

        cutFileSequentialBtn = findViewById(R.id.button_sequential)
        cutFileNonSequentialBtn = findViewById(R.id.button_non_sequential)
        dataFromFileTxt = findViewById(R.id.log_text_view)
        pathFileOutputTxt = findViewById(R.id.path_text_view)
        displayFileContent()

        cutFileSequentialBtn.setOnClickListener {
            cutFileSequential()
            pathFileOutputTxt.text = "Path file output sequential : ${filesDir.absolutePath}"
        }

        cutFileNonSequentialBtn.setOnClickListener {
            cutFileNonSequential()
            pathFileOutputTxt.text = "Path file output non-sequential : ${filesDir.absolutePath}"
        }


    }

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

    private fun readAndWriteFileNonSequentially(file: File, startOffset: Int, outputFileName: String) {
        RandomAccessFile(file, "r").use { raf ->
            val buffer = StringBuilder()
            val lines = mutableListOf<Long>()
            var currentLine = 0
            var currentPos = 0

            while (true) {
                val lineStartPos = raf.filePointer
                val line = raf.readLine() ?: break
                if ((currentLine - startOffset) % 10 == 0 && currentLine >= startOffset) {
                    lines.add(lineStartPos)
                }
                currentPos += line.toByteArray().size + System.lineSeparator().toByteArray().size
                currentLine++
            }

            lines.forEach { pos ->
                raf.seek(pos)
                buffer.appendLine(raf.readLine())
            }

            writeFile(outputFileName, buffer.toString())
        }
    }


    private fun writeFile(outputFileName: String, content: String) {
        val outputFile = File(filesDir, outputFileName)
        BufferedOutputStream(FileOutputStream(outputFile)).use { bos ->
            bos.write(content.toByteArray())
            bos.flush()
        }
        Log.d("FileWritten", "File $outputFileName written successfully ${outputFile.absolutePath}.")
    }

    private fun displayFileContent(){

        CoroutineScope(Dispatchers.IO).launch {
            try {
                assets.open("text.txt").use { inputStream ->
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
    

}
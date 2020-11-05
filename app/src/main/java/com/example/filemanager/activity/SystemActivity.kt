package com.example.filemanager.activity

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.example.filemanager.R
import com.example.filemanager.convertFileSize
import kotlinx.android.synthetic.main.activity_system.*
import java.io.File

@Suppress("DEPRECATION")
class SystemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system)

        val rootDir = File(Environment.getExternalStorageDirectory().absolutePath)

        val convertedSizeFree = convertFileSize(rootDir.freeSpace)
        val convertedSizeTotal = convertFileSize(rootDir.totalSpace)
        memorySize.text = String.format("%.2f ${convertedSizeFree.second} / %.2f ${convertedSizeTotal.second}", convertedSizeFree.first, convertedSizeTotal.first)
    }
}
package com.example.filemanager.list

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.filemanager.R
import com.example.filemanager.services.FileService
import kotlinx.android.synthetic.main.activity_files.*
import java.io.File


class FilesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_files)

        val fileService = FileService()

        val path = intent.getStringExtra(FILES_PATH)!!
        val files = fileService.getList(path)

        title = File(path).name

        listView.adapter = ListAdapter(this, files)

        listView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as File
            fileService.open(selectedItem, this)
        }
    }

    companion object {
        const val FILES_PATH = "filesPath"
    }
}
package com.example.filemanager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    val ROOT_PATH: String = Environment.getExternalStorageDirectory().absolutePath
    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "FileManager"

        val permissionsManager = PermissionManage(this, permissions, 1)

        if (permissionsManager.isGranted()) {
            run()
        } else {
            permissionsManager.request()
        }
    }

    private fun run() {
        val files = getFiles(ROOT_PATH)
        listView.adapter = createListViewAdapter(files)

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as ItemList
            val selectedItemFiles = getFiles(selectedItem.file.absolutePath)
            listView.adapter = createListViewAdapter(selectedItemFiles)
        }
    }

    private fun createListViewAdapter(data: Array<ItemList>) : ArrayAdapter<ItemList> {
        return ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
    }

    private fun getFiles(path: String) : Array<ItemList> {
        return File(path).listFiles()!!
            .map { f -> ItemList(f) }
            .toTypedArray()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    run()
                }
            }
        }
    }
}
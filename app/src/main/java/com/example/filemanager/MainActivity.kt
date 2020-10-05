package com.example.filemanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.annotation.RequiresApi
import com.example.filemanager.list.FilesActivity
import com.example.filemanager.permissions.PermissionManager
import com.example.filemanager.services.FileService
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.math.pow

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val ROOT_PATH: String = Environment.getExternalStorageDirectory().absolutePath
    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissionsManager = PermissionManager(this, permissions, 1)

        if (permissionsManager.isGranted()) {
            run()
        } else {
            permissionsManager.request()
        }
    }

    private fun run() {
        val fileService = FileService()
        val rootDir = File(ROOT_PATH)

        buttonFiles.setOnClickListener { fileService.open(rootDir, this) }
        buttonSystem.setOnClickListener {
            val intent = Intent(this, SystemActivity::class.java)
            startActivity(intent)
        }
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
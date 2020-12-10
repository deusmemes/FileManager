package com.example.filemanager.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.filemanager.R
import com.example.filemanager.permissions.PermissionManager
import com.example.filemanager.service.FileService
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val ROOT_PATH: String = Environment.getExternalStorageDirectory().absolutePath
    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET
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
        val fileService = FileService.getInstance()
        val rootDir = File(ROOT_PATH)

        buttonFiles.setOnClickListener { fileService.open(rootDir, this) }
        buttonSystem.setOnClickListener {
            val intent = Intent(this, SystemActivity::class.java)
            startActivity(intent)
        }
        buttonFavorites.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
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
package com.example.filemanager

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionManage(val context: Activity, val permissions: Array<String>, val code: Int) {
    fun isGranted() : Boolean {
        return permissions.all { p ->
            ActivityCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun request() {
        ActivityCompat.requestPermissions(context, permissions, code)
    }
}
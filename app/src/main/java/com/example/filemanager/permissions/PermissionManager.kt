package com.example.filemanager.permissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.filemanager.interfaces.IPermissionsManager

class PermissionManager(private val context: Activity,
                        private val permissions: Array<String>,
                        private val code: Int): IPermissionsManager
{
    override fun isGranted() : Boolean {
        return permissions.all { p ->
            ActivityCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun request() {
        ActivityCompat.requestPermissions(context, permissions, code)
    }
}
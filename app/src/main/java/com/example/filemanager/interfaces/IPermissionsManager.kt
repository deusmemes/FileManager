package com.example.filemanager.interfaces

interface IPermissionsManager {
    fun isGranted(): Boolean
    fun request()
}
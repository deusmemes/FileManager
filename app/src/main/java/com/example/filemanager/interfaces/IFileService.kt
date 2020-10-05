package com.example.filemanager.interfaces

import android.content.Context
import java.io.File

interface IFileService {
    fun getList(path: String): Array<File>
    fun open(file: File, context: Context)
}
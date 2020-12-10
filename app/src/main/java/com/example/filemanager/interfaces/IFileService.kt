package com.example.filemanager.interfaces

import android.content.Context
import java.io.File

interface IFileService {
    fun getList(path: String): List<File>
    fun open(file: File, context: Context)
    fun upload(sourceFile: File,  context: Context)
}
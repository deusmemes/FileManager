package com.example.filemanager.service

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.example.filemanager.BuildConfig
import com.example.filemanager.activity.FilesActivity
import com.example.filemanager.interfaces.IFileService
import java.io.File

class FileService: IFileService {
    override fun getList(path: String): List<File> {
        return File(path).listFiles()!!
            .sortedWith(compareBy { it.name })
    }

    override fun open(file: File, context: Context) {
        if (file.isDirectory) {
            openDir(file, context)
        } else {
            openFile(file, context)
        }
    }

    private fun openFile(file: File, context: Context) {
        val intent = Intent()
            .setAction(Intent.ACTION_VIEW)
            .setData(FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file.absoluteFile))

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent)
    }

    private fun openDir(dir: File, context: Context) {
        val intent = Intent(context, FilesActivity::class.java)
        intent.putExtra(FilesActivity.FILES_PATH, dir.absolutePath)
        context.startActivity(intent)
    }
}
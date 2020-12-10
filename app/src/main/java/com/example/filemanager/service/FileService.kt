package com.example.filemanager.service

import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.filemanager.BuildConfig
import com.example.filemanager.activity.FilesActivity
import com.example.filemanager.interfaces.IFileService
import com.example.filemanager.showToast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FileService: IFileService {
    private val client = OkHttpClient()
    private val serverURL = "http://192.168.0.150:5000/upload"

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

    override fun upload(sourceFile: File, context: Context) {
        Thread {
            val mimeType = getMimeType(sourceFile) ?: return@Thread
            val fileName = sourceFile.name
            try {
                val requestBody: RequestBody =
                    MultipartBody
                        .Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", fileName, sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                        .build()

                val request: Request = Request.Builder()
                    .url(serverURL)
                    .post(requestBody)
                    .build()
                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    Log.d("upload", "Upload success")
                    showToast("Файл успешно загружен на сервер", context)
                } else {
                    Log.e("upload", "Upload error")
                    showToast("Ошибка загрузки", context)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("upload", "Upload error")
                showToast("Ошибка загрузки", context)
            }
        }.start()
    }

    private fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
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

    companion object {
        private var instance: FileService? = null

        fun getInstance(): FileService {
            if (instance == null) {
                instance = FileService()
            }

            return instance!!
        }
    }
}
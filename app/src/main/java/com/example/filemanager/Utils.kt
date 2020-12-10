package com.example.filemanager

import android.app.Activity
import android.content.Context
import android.widget.Toast

private const val b = 1024
private const val kb = b * 1024
private const val mb = kb * 1024
private const val gb: Long = mb.toLong() * 1024

fun convertFileSize(fileSize: Long): Pair<Double, String> {
    val size = fileSize.toDouble()

    if (size < b) return Pair(size, "B")
    if (size < kb) return Pair(size / b, "KB")
    if (size < mb) return Pair(size / kb, "MB")
    if (size < gb) return Pair(size / mb, "GB")

    return Pair(0.0, "B")
}

fun showToast(message: String, context: Context) {
    (context as Activity).runOnUiThread {
        Toast.makeText(context, message, Toast.LENGTH_SHORT ).show()
    }
}
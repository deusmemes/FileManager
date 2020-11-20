package com.example.filemanager

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
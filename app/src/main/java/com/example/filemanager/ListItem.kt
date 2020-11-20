package com.example.filemanager

import java.io.File

data class ListItem(
    val file: File,
    val isFavorite: Boolean
)
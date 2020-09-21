package com.example.filemanager

import java.io.File

data class ItemList(val file: File) {
    override fun toString(): String {
        return file.name
    }
}
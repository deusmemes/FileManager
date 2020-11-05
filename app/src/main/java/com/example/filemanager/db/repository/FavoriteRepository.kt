package com.example.filemanager.db.repository

import com.example.filemanager.db.dao.FavoriteDao
import com.example.filemanager.db.entity.Favorite

class FavoriteRepository(private val dao: FavoriteDao) {
    val favorites = dao.getAll()

    suspend fun insert(favorite: Favorite) {
        dao.insert(favorite)
    }

    suspend fun delete(favorite: Favorite) {
        dao.delete(favorite)
    }

    suspend fun getByPath(path: String): Favorite? {
        return dao.getByPath(path)
    }
}
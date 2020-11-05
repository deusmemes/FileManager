package com.example.filemanager.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.filemanager.db.entity.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Update
    suspend fun update(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    fun getAll(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorites WHERE path = :path LIMIT 1")
    suspend fun getByPath(path: String): Favorite?
}
package com.example.filemanager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.filemanager.db.dao.FavoriteDao
import com.example.filemanager.db.entity.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteDao: FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_db"
                    ).build()
                }

                return instance
            }
        }
    }
}
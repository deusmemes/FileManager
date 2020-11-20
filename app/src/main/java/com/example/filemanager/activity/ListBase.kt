package com.example.filemanager.activity

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.filemanager.ListItem
import com.example.filemanager.db.AppDatabase
import com.example.filemanager.db.repository.FavoriteRepository
import com.example.filemanager.dialogs.DeleteDialog
import com.example.filemanager.service.FileService
import com.example.filemanager.viewmodel.FavoriteViewModel
import com.example.filemanager.viewmodel.factory.FavoriteViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

open class ListBase(private val context: Context, application: Application) {
    var favoriteViewModel: FavoriteViewModel

    init {
        val dao = AppDatabase.getInstance(application).favoriteDao
        val repository = FavoriteRepository(dao)
        val factory = FavoriteViewModelFactory(repository)
        favoriteViewModel = ViewModelProvider(context as AppCompatActivity, factory).get(FavoriteViewModel::class.java)
    }

    fun listItemClicked(file: File) {
        FileService().open(file, context)
    }

    fun listItemFavoriteClicked(item: ListItem) {
        if (item.isFavorite) {
            showDialog { toggleListItem(item.file)}
        } else {
            toggleListItem(item.file)
        }
    }

    private fun showDialog(listener: () -> Job) {
        val dialog = DeleteDialog(listener)
        val activity = context as AppCompatActivity
        dialog.show(activity.supportFragmentManager, "delete")
    }

    fun toggleListItem(file: File) = GlobalScope.launch {
        favoriteViewModel.toggle(file.absolutePath)
    }
}
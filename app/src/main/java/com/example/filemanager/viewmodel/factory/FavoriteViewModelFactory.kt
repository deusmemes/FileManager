package com.example.filemanager.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filemanager.db.repository.FavoriteRepository
import com.example.filemanager.viewmodel.FavoriteViewModel
import java.lang.IllegalArgumentException

class FavoriteViewModelFactory(private val repository: FavoriteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) return FavoriteViewModel(
            repository
        ) as T

        throw IllegalArgumentException("Unknown View Model class")
    }
}
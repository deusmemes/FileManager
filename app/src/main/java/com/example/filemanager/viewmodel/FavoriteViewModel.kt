package com.example.filemanager.viewmodel

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filemanager.db.entity.Favorite
import com.example.filemanager.db.repository.FavoriteRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel(), Observable {
    val favorites = repository.favorites

    suspend fun toggle(path: String) {
        if (!isInFavorites(path)) insert(Favorite(0, path))
        else delete(repository.getByPath(path)!!)
    }

    fun insert(favorite: Favorite) = viewModelScope.launch {
        repository.insert(favorite)
    }

    fun delete(favorite: Favorite) = viewModelScope.launch {
        repository.delete(favorite)
    }

    fun getByPath(path: String): Deferred<Favorite?> = viewModelScope.async {
        return@async repository.getByPath(path)
    }

    suspend fun isInFavorites(path: String): Boolean {
        val def = viewModelScope.async { repository.getByPath(path) }
        return def.await() != null
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}
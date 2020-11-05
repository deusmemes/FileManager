package com.example.filemanager.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filemanager.db.entity.Favorite
import com.example.filemanager.db.repository.FavoriteRepository
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

    suspend fun isInFavorites(path: String): Boolean {
        val def = viewModelScope.async { repository.getByPath(path) }
//        Log.i("MYTAG", def.await().toString())
        return def.await() != null
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}
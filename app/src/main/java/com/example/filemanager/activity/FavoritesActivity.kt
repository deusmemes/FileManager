package com.example.filemanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.filemanager.R
import com.example.filemanager.viewmodel.FavoriteViewModel
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filemanager.RecyclerViewAdapter
import com.example.filemanager.databinding.ActivityFavoritesBinding
import com.example.filemanager.db.AppDatabase
import com.example.filemanager.db.repository.FavoriteRepository
import com.example.filemanager.viewmodel.factory.FavoriteViewModelFactory
import java.io.File

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorites)
        title = "Избранное"
        val dao = AppDatabase.getInstance(application).favoriteDao
        val repository = FavoriteRepository(dao)
        val factory =
            FavoriteViewModelFactory(repository)
        favoriteViewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)
        binding.myViewModel = favoriteViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayFavoriteList()
    }

    private fun displayFavoriteList() {
        favoriteViewModel.favorites.observe(
            this,
            Observer {
                Log.i("MYTAG", it.toString())
                binding.recyclerView.adapter = RecyclerViewAdapter(this, it.map { f -> File(f.path) }, favoriteViewModel)
            }
        )
    }
}
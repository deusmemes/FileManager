package com.example.filemanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.filemanager.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filemanager.ListItem
import com.example.filemanager.RecyclerViewAdapter
import com.example.filemanager.databinding.ActivityFavoritesBinding
import com.example.filemanager.dialogs.DeleteDialog
import java.io.File

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var base: ListBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorites)
        title = "Избранное"
        base = ListBase(this, application)
        binding.myViewModel = base.favoriteViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayFavoriteList()
    }

    private fun displayFavoriteList() {
        base.favoriteViewModel.favorites.observe(
            this,
            Observer {
                Log.i("MYTAG", it.toString())
                binding.recyclerView.adapter = RecyclerViewAdapter(
                    it.map { f -> ListItem(File(f.path), true) },
                    { selectedItem: File -> base.listItemClicked(selectedItem)},
                    { selectedItem: ListItem -> base.listItemFavoriteClicked(selectedItem) })
            }
        )
    }
}
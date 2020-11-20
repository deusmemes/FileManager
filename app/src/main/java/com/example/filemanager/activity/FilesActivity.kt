package com.example.filemanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filemanager.ListItem
import com.example.filemanager.R
import com.example.filemanager.RecyclerViewAdapter
import com.example.filemanager.databinding.ActivityFilesBinding
import com.example.filemanager.db.AppDatabase
import com.example.filemanager.db.repository.FavoriteRepository
import com.example.filemanager.service.FileService
import com.example.filemanager.viewmodel.FavoriteViewModel
import com.example.filemanager.viewmodel.factory.FavoriteViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class FilesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilesBinding
    private lateinit var base: ListBase
    private lateinit var fileService: FileService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_files)
        fileService = FileService()
        val path = intent.getStringExtra(FILES_PATH)!!
        title = File(path).name
        base = ListBase(this, application)
        binding.myViewModel = base.favoriteViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerViewFiles.layoutManager = LinearLayoutManager(this)
        displayList()
    }

    private fun displayList() {
        val files = fileService.getList(intent.getStringExtra(FILES_PATH)!!)
        base.favoriteViewModel.favorites.observe(
            this,
            Observer {
                Log.i("MYTAG", it.toString())
                binding.recyclerViewFiles.adapter = RecyclerViewAdapter(
                    files.map { f -> ListItem(f, it.find { fav -> fav.path == f.absolutePath } != null) },
                    { selectedItem: File -> base.listItemClicked(selectedItem)},
                    { selectedItem: ListItem -> base.listItemFavoriteClicked(selectedItem) })
            }
        )
    }

    companion object {
        const val FILES_PATH = "filesPath"
    }
}
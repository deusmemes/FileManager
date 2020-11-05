package com.example.filemanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filemanager.ListAdapter
import com.example.filemanager.R
import com.example.filemanager.RecyclerViewAdapter
import com.example.filemanager.databinding.ActivityFilesBinding
import com.example.filemanager.db.AppDatabase
import com.example.filemanager.db.repository.FavoriteRepository
import com.example.filemanager.service.FileService
import com.example.filemanager.viewmodel.FavoriteViewModel
import com.example.filemanager.viewmodel.factory.FavoriteViewModelFactory
import java.io.File

class FilesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilesBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var fileService: FileService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_files)
        fileService = FileService()
        val path = intent.getStringExtra(FILES_PATH)!!
        title = File(path).name
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
        binding.recyclerViewFiles.layoutManager = LinearLayoutManager(this)
        displayFavoriteList()
    }

    private fun displayFavoriteList() {
        val files = fileService.getList(intent.getStringExtra(FILES_PATH)!!)
        binding.recyclerViewFiles.adapter = RecyclerViewAdapter(this, files, favoriteViewModel)
    }

    companion object {
        const val FILES_PATH = "filesPath"
    }
}
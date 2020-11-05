package com.example.filemanager

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.databinding.ListItemBinding
import com.example.filemanager.service.FileService
import com.example.filemanager.viewmodel.FavoriteViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class RecyclerViewAdapter(
    private val context: Context,
    private val files: List<File>,
    private val viewModel: FavoriteViewModel
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return ViewHolder(binding, context, viewModel)
    }

    override fun getItemCount(): Int {
        return files.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(files[position])
    }
}

class ViewHolder(val binding: ListItemBinding, val context: Context, val viewModel: FavoriteViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun bind(file: File) {
        binding.listRowText.text = file.name
        if (file.isFile) {
            val formatSize = convertFileSize(file.length())
            binding.listRowDescription.text = String.format("%.2f ${formatSize.second}", formatSize.first)
        }
        else binding.listRowDescription.text = "${file.listFiles()!!.size} элементов"
        if (file.isDirectory) {
            binding.listRowIcon.setImageResource(R.drawable.ic_baseline_folder_24)
        } else {
            binding.listRowIcon.setImageResource(getFileIcon(file))
        }

        binding.root.setOnClickListener { FileService().open(file, context) }
        binding.favoriteIcon.setOnClickListener {
            GlobalScope.launch { viewModel.toggle(file.absolutePath) }
        }
    }

    private fun getFileIcon(file: File): Int {
        when(file.extension) {
            "pdf" -> return R.drawable.ic_baseline_picture_as_pdf_24
            "png", "jpg", "jpeg", "tiff", "svg" -> return R.drawable.ic_baseline_image_24
            "mp4", "avi", "mov", "wmv" -> return R.drawable.ic_baseline_videocam_24
            "mp3", "aac", "wma", "m4a", "opus" -> return R.drawable.ic_baseline_audiotrack_24
        }

        return R.drawable.ic_baseline_insert_drive_file_24
    }
}
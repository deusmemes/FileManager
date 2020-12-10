package com.example.filemanager

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.databinding.ListItemBinding
import java.io.File

class RecyclerViewAdapter(
    private val files: List<ListItem>,
    private val clickListener: (File) -> Unit,
    private val toggleListener: (ListItem) -> Unit,
    private val uploadFile: (File) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        Log.d("MYTAG", files.toString())
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return files.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(files[position], clickListener, uploadFile, toggleListener)
    }
}

class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ListItem, clickListener:(File) -> Unit, uploadFile:(File) -> Unit, toggleListener: (ListItem) -> Unit) {
        item.file.let {
            setListItemName(it)
            setListItemText(it)
            setListItemIcon(it)
            setListItemUpload(it)
        }

        if (item.isFavorite) binding.favoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
        else binding.favoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        binding.listItemLayout.setOnClickListener { clickListener(item.file) }
        binding.favoriteIcon.setOnClickListener { toggleListener(item) }
        binding.uploadIcon.setOnClickListener { uploadFile(item.file) }
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

    private fun setListItemIcon(file: File) {
        if (file.isDirectory) {
            binding.listRowIcon.setImageResource(R.drawable.ic_baseline_folder_24)
        } else {
            binding.listRowIcon.setImageResource(getFileIcon(file))
        }
    }

    private fun setListItemUpload(file: File) {
        if (file.isFile) binding.uploadIcon.visibility = View.VISIBLE
        else binding.uploadIcon.visibility = View.INVISIBLE
    }

    private fun setListItemName(file: File) {
        binding.listRowText.text = file.name
    }

    private fun setListItemText(file: File) {
        if (file.isFile) {
            val formatSize = convertFileSize(file.length())
            binding.listRowDescription.text = String.format("%.2f ${formatSize.second}", formatSize.first)
        }
        else binding.listRowDescription.text = "${file.listFiles().size} элементов"
    }
}
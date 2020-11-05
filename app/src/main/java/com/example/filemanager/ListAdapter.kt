package com.example.filemanager

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.filemanager.R
import com.example.filemanager.convertFileSize
import java.io.File


class ListAdapter(private val context: Context): BaseAdapter() {
    private var data = emptyList<File>()
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) view = inflater.inflate(R.layout.list_row, null)!!

        val currentItem = data[position]

        val textView = view.findViewById<View>(R.id.list_row_text) as TextView
        textView.text = data[position].name

        val description = view.findViewById<View>(R.id.list_row_description) as TextView

        if (currentItem.isFile) {
            val formatSize = convertFileSize(currentItem.length())
            description.text = String.format("%.2f ${formatSize.second}", formatSize.first)
        }
        else description.text = "${currentItem.listFiles()!!.size} файлов"

        val imageView: ImageView = view.findViewById(R.id.list_row_icon) as ImageView
        if (currentItem.isDirectory) {
            imageView.setImageResource(R.drawable.ic_baseline_folder_24)
        } else {
            imageView.setImageResource(getFileIcon(currentItem))
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
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

    fun setAdapterData(adapterData: List<File>) {
        this.data = adapterData
    }
}
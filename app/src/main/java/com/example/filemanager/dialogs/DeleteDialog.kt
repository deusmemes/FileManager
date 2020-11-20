package com.example.filemanager.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.filemanager.R
import kotlinx.coroutines.Job

class DeleteDialog(private val listener: () -> Job) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return builder
            .setTitle("Удаление")
            .setMessage("Вы действительно хотите удалить объект из списка избранного?")
            .setIcon(R.drawable.ic_baseline_delete_24)
            .setView(R.layout.delete_dialog)
            .setPositiveButton("Да") { _, _ -> listener.invoke() }
            .setNegativeButton("Отмена", null)
            .create()
    }
}
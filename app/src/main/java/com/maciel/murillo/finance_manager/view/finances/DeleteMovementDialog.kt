package com.maciel.murillo.finance_manager.view.finances

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.maciel.murillo.finance_manager.R

class DeleteMovementDialog : DialogFragment() {

    private lateinit var onClickDelete: () -> Unit
    private lateinit var onClickCancel: () -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(R.string.confirm_delete)
        builder.setPositiveButton(R.string.yes) { _, _ ->
            onClickDelete.invoke()
        }
        builder.setNegativeButton(R.string.no) { _, _ ->
            onClickCancel.invoke()
        }
        return builder.create()
    }

    companion object {
        fun show(manager: FragmentManager, onClickDelete: () -> Unit, onClickCancel: () -> Unit) {
            val dialog = DeleteMovementDialog()
            dialog.onClickDelete = onClickDelete
            dialog.onClickCancel = onClickCancel
            dialog.show(manager, DeleteMovementDialog::class.java.name)
        }
    }
}
package ru.maxultra.durakhelper

import android.app.AlertDialog
import android.content.Context

object ConfirmDialog {
    fun show(context: Context?, onYesAction: () -> Unit) {
        AlertDialog.Builder(context)
            .setIcon(android.R.drawable.ic_delete)
            .setTitle(R.string.alert_title)
            .setMessage(R.string.alert_question)
            .setPositiveButton(R.string.yes_button) { _, _ -> onYesAction() }
            .setNegativeButton(R.string.no_button, null)
            .show()
    }
}

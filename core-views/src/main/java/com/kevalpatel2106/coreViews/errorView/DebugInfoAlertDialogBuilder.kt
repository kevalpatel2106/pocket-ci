package com.kevalpatel2106.coreViews.errorView

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kevalpatel2106.coreViews.R
import com.kevalpatel2106.entity.DisplayError

internal class DebugInfoAlertDialogBuilder(context: Context) : MaterialAlertDialogBuilder(context) {

    private val clipboardManager by lazy {
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    fun show(displayError: DisplayError) {
        setTitle(R.string.error_debug_info_dialog_title)
            .setMessage(displayError.toString())
            .setPositiveButton(R.string.error_debug_info_dialog_copy_button) { _, _ ->
                val clip = ClipData.newPlainText(
                    context.getString(R.string.error_debug_info_dialog_title),
                    displayError.toString(),
                )
                clipboardManager.setPrimaryClip(clip)
                Toast.makeText(context, R.string.error_debug_info_copied, Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.error_debug_info_dialog_close_button) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .show()
    }
}

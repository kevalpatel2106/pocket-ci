package com.kevalpatel2106.core.ui.errorHandling

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.entity.DisplayError

fun Fragment.showErrorSnack(
    displayError: DisplayError,
    @StringRes messageRes: Int = Resources.ID_NULL,
): Snackbar {
    return showSnack(
        message = if (messageRes == Resources.ID_NULL) {
            displayError.message
        } else {
            getString(messageRes)
        },
        actonTitle = R.string.error_snack_more_button_title,
        actionListener = { DebugInfoAlertDialogBuilder(requireContext()).show(displayError) },
    )
}

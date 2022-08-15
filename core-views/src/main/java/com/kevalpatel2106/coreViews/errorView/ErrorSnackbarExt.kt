package com.kevalpatel2106.coreViews.errorView

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.coreViews.R

fun Fragment.showErrorSnack(
    throwable: Throwable,
    displayErrorMapper: DisplayErrorMapper,
): Snackbar {
    val displayError = displayErrorMapper(throwable, shortMessage = true)
    return showSnack(
        message = displayError.message,
        actonTitle = R.string.error_snack_more_button_title,
        actionListener = { DebugInfoAlertDialogBuilder(requireContext()).show(displayError) },
    )
}

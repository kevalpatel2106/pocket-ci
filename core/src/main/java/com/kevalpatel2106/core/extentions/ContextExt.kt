package com.kevalpatel2106.core.extentions

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

const val SNACK_BAR_DURATION: Int = 2000

/**
 * Display the snack bar.
 */
@SuppressLint("WrongConstant")
fun Fragment.showSnack(
    message: String,
    duration: Int = SNACK_BAR_DURATION,
    actonTitle: Int = ResourcesCompat.ID_NULL,
    actionListener: ((View) -> Unit)? = null,
    dismissListener: (() -> Unit)? = null,
): Snackbar {
    val snackBar = Snackbar.make(
        requireActivity().findViewById<ViewGroup>(android.R.id.content).getChildAt(0) as ViewGroup,
        message,
        duration,
    ).apply {
        actonTitle.takeIf { it != ResourcesCompat.ID_NULL }?.let { title ->
            setAction(title, actionListener)
        }

        addCallback(
            object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    dismissListener?.invoke()
                }
            },
        )
    }
    snackBar.show()
    return snackBar
}

fun Fragment.showSnack(
    @StringRes messageRes: Int,
    duration: Int = SNACK_BAR_DURATION,
    actonTitle: Int = ResourcesCompat.ID_NULL,
    actionListener: ((View) -> Unit)? = null,
    dismissListener: (() -> Unit)? = null,
) = showSnack(
    requireContext().getString(messageRes),
    duration,
    actonTitle,
    actionListener,
    dismissListener,
)

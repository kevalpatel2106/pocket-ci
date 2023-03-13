package com.kevalpatel2106.feature.artifact.list.eventHandler

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.feature.artifact.list.model.ArtifactListViewState
import javax.inject.Inject

internal class ArtifactListViewStateHandlerImpl @Inject constructor(
    private val fragment: Fragment,
) : ArtifactListViewStateHandler {

    private val downloadingSnackBar by lazy(LazyThreadSafetyMode.NONE) {
        Snackbar.make(
            fragment.requireActivity()
                .findViewById<ViewGroup>(android.R.id.content)
                .getChildAt(0) as ViewGroup,
            R.string.artifact_list_downloading_in_progress_message,
            Snackbar.LENGTH_INDEFINITE,
        )
    }

    override fun invoke(viewState: ArtifactListViewState): Unit = with(fragment) {
        viewState.toolbarTitle?.let { requireActivity().title = it }
        if (viewState.showDownloadingLoader) {
            downloadingSnackBar.show()
        } else {
            downloadingSnackBar.dismiss()
        }
    }
}
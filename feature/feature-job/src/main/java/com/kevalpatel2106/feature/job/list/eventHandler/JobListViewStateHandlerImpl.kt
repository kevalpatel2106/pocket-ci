package com.kevalpatel2106.feature.job.list.eventHandler

import androidx.fragment.app.Fragment
import com.kevalpatel2106.feature.job.list.model.JobListViewState
import javax.inject.Inject

internal class JobListViewStateHandlerImpl @Inject constructor(
    private val fragment: Fragment,
) : JobListViewStateHandler {

    override operator fun invoke(viewState: JobListViewState): Unit = with(fragment) {
        viewState.toolbarTitle?.let { requireActivity().title = it }
    }
}

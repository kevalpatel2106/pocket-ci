package com.kevalpatel2106.feature.build.list.eventHandler

import androidx.fragment.app.Fragment
import com.kevalpatel2106.feature.build.list.model.BuildListViewState
import javax.inject.Inject

internal class BuildListViewStateHandlerImpl @Inject constructor(
    private val fragment: Fragment
) : BuildListViewStateHandler {

    override fun invoke(viewState: BuildListViewState) {
        fragment.requireActivity().title = viewState.toolbarTitle
    }
}
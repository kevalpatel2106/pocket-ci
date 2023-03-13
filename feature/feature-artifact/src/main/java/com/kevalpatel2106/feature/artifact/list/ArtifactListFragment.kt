package com.kevalpatel2106.feature.artifact.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.collectStateInFragment
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.ui.extension.setContent
import com.kevalpatel2106.feature.artifact.list.eventHandler.ArtifactListViewStateHandler
import com.kevalpatel2106.feature.artifact.list.eventHandler.ArtifactListVmEventHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtifactListFragment : Fragment() {
    private val viewModel by viewModels<ArtifactListListViewModel>()

    @Inject
    internal lateinit var displayErrorMapper: DisplayErrorMapper

    @Inject
    internal lateinit var vmEventHandler: ArtifactListVmEventHandler

    @Inject
    internal lateinit var viewStateHandler: ArtifactListViewStateHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { ArtifactListScreen(displayErrorMapper = displayErrorMapper) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, vmEventHandler::invoke)
        viewModel.viewState.collectStateInFragment(this, viewStateHandler::invoke)
    }
}

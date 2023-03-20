package com.kevalpatel2106.feature.build.list

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
import com.kevalpatel2106.feature.build.list.eventHandler.BuildListViewStateHandler
import com.kevalpatel2106.feature.build.list.eventHandler.BuildListVmEventHandler
import com.kevalpatel2106.feature.build.list.screen.BuildListScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class BuildListFragment : Fragment() {
    private val viewModel by viewModels<BuildListViewModel>()

    @Inject
    internal lateinit var displayErrorMapper: DisplayErrorMapper

    @Inject
    internal lateinit var vmEventHandler: BuildListVmEventHandler

    @Inject
    internal lateinit var viewStateHandler: BuildListViewStateHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { BuildListScreen(displayErrorMapper = displayErrorMapper) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.collectStateInFragment(this, viewStateHandler::invoke)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, vmEventHandler::invoke)
    }
}

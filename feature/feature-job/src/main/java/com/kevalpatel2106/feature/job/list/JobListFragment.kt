package com.kevalpatel2106.feature.job.list

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
import com.kevalpatel2106.feature.job.list.eventHandler.JobListViewStateHandler
import com.kevalpatel2106.feature.job.list.eventHandler.JobListVmEventHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class JobListFragment : Fragment() {
    private val viewModel by viewModels<JobListViewModel>()

    @Inject
    internal lateinit var displayErrorMapper: DisplayErrorMapper

    @Inject
    internal lateinit var vmEventHandler: JobListVmEventHandler

    @Inject
    internal lateinit var viewStateHandler: JobListViewStateHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { JobListScreen(displayErrorMapper = displayErrorMapper) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.collectStateInFragment(this, viewStateHandler::invoke)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, vmEventHandler::invoke)
    }
}

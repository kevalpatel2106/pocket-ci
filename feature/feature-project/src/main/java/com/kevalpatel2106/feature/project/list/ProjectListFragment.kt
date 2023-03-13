package com.kevalpatel2106.feature.project.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.ui.extension.setContent
import com.kevalpatel2106.feature.project.list.eventHandler.ProjectListVMEventHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProjectListFragment : Fragment() {
    private val viewModel by viewModels<ProjectListViewModel>()

    @Inject
    lateinit var displayErrorMapper: DisplayErrorMapper

    @Inject
    internal lateinit var vMEventHandler: ProjectListVMEventHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { ProjectListScreen(displayErrorMapper) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, vMEventHandler::invoke)
    }
}

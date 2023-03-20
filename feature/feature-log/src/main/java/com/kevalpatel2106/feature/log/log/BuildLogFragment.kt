package com.kevalpatel2106.feature.log.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.extension.setContent
import com.kevalpatel2106.feature.log.log.eventHandler.BuildLogVmEventHandler
import com.kevalpatel2106.feature.log.log.menu.BuildLogMenuProvider
import com.kevalpatel2106.feature.log.log.screen.BuildLogScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BuildLogFragment : Fragment() {
    private val viewModel by viewModels<BuildLogViewModel>()
    private val navArgs by navArgs<BuildLogFragmentArgs>()

    @Inject
    internal lateinit var buildLogVmEventHandler: BuildLogVmEventHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { BuildLogScreen() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTitle()
        BuildLogMenuProvider.bindWithLifecycle(this, viewModel)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, buildLogVmEventHandler::invoke)
    }

    private fun setUpTitle() {
        val titleRes = if (navArgs.jobId.isNullOrBlank()) {
            R.string.title_build_logs_screen
        } else {
            R.string.title_job_logs_screen
        }
        requireActivity().title = getString(titleRes)
    }
}

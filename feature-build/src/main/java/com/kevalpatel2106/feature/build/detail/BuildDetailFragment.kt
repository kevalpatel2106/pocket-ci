package com.kevalpatel2106.feature.build.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.feature.build.R
import com.kevalpatel2106.feature.build.databinding.FragmentBuildDetailBinding
import com.kevalpatel2106.feature.build.detail.BuildDetailVMEvent.OpenBuildLogs
import com.kevalpatel2106.feature.build.detail.BuildDetailVMEvent.OpenMarkDownViewer
import com.kevalpatel2106.feature.build.detail.BuildDetailVMEvent.ShowErrorAndClose
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuildDetailFragment : Fragment(R.layout.fragment_build_detail) {
    private val viewModel by viewModels<BuildDetailViewModel>()
    private val binding by viewBinding(FragmentBuildDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        viewModel.vmEventsFlow.collectInFragment(this, ::handleVMEvents)
    }

    private fun handleVMEvents(event: BuildDetailVMEvent) {
        when (event) {
            is OpenBuildLogs -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.BuildLog(
                    accountId = event.accountId,
                    projectId = event.projectId,
                    buildId = event.buildId,
                ),
            )
            is OpenMarkDownViewer -> findNavController().navigate(
                BuildDetailFragmentDirections.actionBuildDetailToMarkdownView(
                    getString(event.titleRes),
                    event.commitMessage,
                ),
            )
            ShowErrorAndClose -> {
                showSnack(getString(R.string.error_unknown_message_short))
                findNavController().navigateUp()
            }
            is BuildDetailVMEvent.OpenJobs -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.JobsList(
                    accountId = event.accountId,
                    projectId = event.projectId,
                    buildId = event.buildId,
                    title = event.title,
                ),
            )
        }
    }
}

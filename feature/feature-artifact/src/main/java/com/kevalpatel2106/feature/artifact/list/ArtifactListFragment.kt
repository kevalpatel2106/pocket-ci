package com.kevalpatel2106.feature.artifact.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.collectStateInFragment
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.ui.extension.setContent
import com.kevalpatel2106.core.baseUi.showErrorSnack
import com.kevalpatel2106.feature.artifact.R
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.Close
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowArtifactRemoved
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadFailed
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadNotSupported
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadQueuedMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtifactListFragment : Fragment() {

    @Inject
    lateinit var displayErrorMapper: DisplayErrorMapper

    private val viewModel by viewModels<ArtifactListListViewModel>()

    private val downloadingSnackBar by lazy(LazyThreadSafetyMode.NONE) {
        Snackbar.make(
            requireActivity().findViewById<ViewGroup>(android.R.id.content)
                .getChildAt(0) as ViewGroup,
            R.string.artifact_list_downloading_in_progress_message,
            Snackbar.LENGTH_INDEFINITE,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setContent { ArtifactListScreen(displayErrorMapper = displayErrorMapper) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, ::handleVMEvent)
        viewModel.viewState.collectStateInFragment(this, ::handleViewState)
    }

    private fun handleVMEvent(event: ArtifactListVMEvent) {
        when (event) {
            Close -> findNavController().navigateUp()
            is ShowDownloadFailed -> {
                showErrorSnack(event.error, R.string.artifact_list_download_queue_error)
            }

            ShowDownloadQueuedMessage -> showSnack(R.string.artifact_list_download_queued_successfully)
            ShowArtifactRemoved -> showSnack(R.string.artifact_list_download_artifact_removed)
            ShowDownloadNotSupported -> showSnack(R.string.artifact_list_download_not_supported)
        }
    }

    private fun handleViewState(event: ArtifactListViewState) = with(event) {
        event.toolbarTitle?.let { requireActivity().title = it }
        if (event.showDownloadingLoader) {
            downloadingSnackBar.show()
        } else {
            downloadingSnackBar.dismiss()
        }
    }
}

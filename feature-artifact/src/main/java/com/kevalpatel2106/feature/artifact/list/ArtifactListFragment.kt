package com.kevalpatel2106.feature.artifact.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.extentions.isEmptyList
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateAdapter
import com.kevalpatel2106.feature.artifact.R
import com.kevalpatel2106.feature.artifact.databinding.FragmentArtifactListBinding
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.Close
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.RefreshArtifacts
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.RetryLoading
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadFailed
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadQueuedMessage
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ArtifactListFragment : Fragment(R.layout.fragment_artifact_list) {
    private val binding by viewBinding(FragmentArtifactListBinding::bind)
    private val viewModel by viewModels<ArtifactListViewModel>()
    private val artifactAdapter by lazy(LazyThreadSafetyMode.NONE) { ArtifactAdapter(viewModel) }
    private val downloadingSnackBar by lazy(LazyThreadSafetyMode.NONE) {
        Snackbar.make(
            binding.root,
            R.string.artifact_list_downloading_in_progress_message,
            Snackbar.LENGTH_INDEFINITE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        prepareRecyclerView()
        observeAdapterLoadState()
        binding.artifactListSwipeRefresh.setOnRefreshListener(viewModel::reload)
        viewModel.pageViewState.collectInFragment(this, artifactAdapter::submitData)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleVMEvent)
        viewModel.viewState.collectInFragment(this, ::handleViewState)
    }

    private fun prepareRecyclerView() = with(binding.artifactListRecyclerView) {
        adapter = artifactAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    }

    private fun observeAdapterLoadState() = with(binding) {
        artifactAdapter.loadStateFlow.collectInFragment(this@ArtifactListFragment) { loadState ->
            artifactListSwipeRefresh.isRefreshing = false
            val sourceStates = loadState.source
            val refreshStates = loadState.refresh
            artifactListViewFlipper.displayedChild = when {
                refreshStates is LoadState.Error -> {
                    Timber.e(refreshStates.error)
                    artifactListErrorView.setErrorThrowable(refreshStates.error)
                    POS_ERROR
                }
                sourceStates.refresh is LoadState.Loading -> POS_LOADER
                sourceStates.isEmptyList(artifactAdapter) -> POS_EMPTY_VIEW
                else -> POS_LIST
            }
        }
    }

    private fun handleVMEvent(event: ArtifactListVMEvent) {
        when (event) {
            RetryLoading -> artifactAdapter.retry()
            RefreshArtifacts -> artifactAdapter.refresh()
            Close -> findNavController().navigateUp()
            ShowDownloadFailed -> {
                showSnack(getString(R.string.artifact_list_download_queue_error))
            }
            ShowDownloadQueuedMessage -> {
                showSnack(getString(R.string.artifact_list_download_queued_successfully))
            }
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

    companion object {
        private const val POS_EMPTY_VIEW = 3
        private const val POS_ERROR = 2
        private const val POS_LIST = 1
        private const val POS_LOADER = 0
    }
}

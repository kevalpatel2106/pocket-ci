package com.kevalpatel2106.feature.artifact.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.paging.FirstPageLoadState.Empty
import com.kevalpatel2106.core.paging.FirstPageLoadState.Error
import com.kevalpatel2106.core.paging.FirstPageLoadState.Loaded
import com.kevalpatel2106.core.paging.FirstPageLoadState.Loading
import com.kevalpatel2106.core.paging.usecase.LoadStateMapper
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.coreViews.errorView.showErrorSnack
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateAdapter
import com.kevalpatel2106.feature.artifact.R
import com.kevalpatel2106.feature.artifact.databinding.FragmentArtifactListBinding
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.Close
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.RefreshArtifacts
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.RetryLoading
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowArtifactRemoved
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadFailed
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadNotSupported
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadQueuedMessage
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowErrorLoadingArtifact
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class ArtifactListFragment : Fragment(R.layout.fragment_artifact_list) {
    private val binding by viewBinding(FragmentArtifactListBinding::bind)
    private val viewModel by viewModels<ArtifactListListViewModel>()
    private val artifactListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ArtifactListAdapter(viewModel)
    }
    private val downloadingSnackBar by lazy(LazyThreadSafetyMode.NONE) {
        Snackbar.make(
            binding.root,
            R.string.artifact_list_downloading_in_progress_message,
            Snackbar.LENGTH_INDEFINITE,
        )
    }

    @Inject
    lateinit var loadStateMapper: LoadStateMapper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        prepareRecyclerView()
        observeAdapterLoadState()
        binding.artifactListSwipeRefresh.setOnRefreshListener(viewModel::reload)
        viewModel.pageViewState.collectInFragment(this, artifactListAdapter::submitData)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleVMEvent)
        viewModel.viewState.collectInFragment(this, ::handleViewState)
    }

    private fun prepareRecyclerView() = with(binding.artifactListRecyclerView) {
        adapter = artifactListAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    }

    private fun observeAdapterLoadState() = artifactListAdapter.loadStateFlow
        .map { loadStateMapper(artifactListAdapter, it) }
        .collectInFragment(this) { state ->
            binding.artifactListSwipeRefresh.isRefreshing = false
            binding.artifactListViewFlipper.displayedChild = when (state) {
                is Error -> {
                    binding.artifactListErrorView.setErrorThrowable(state.error)
                    POS_ERROR
                }
                Loading -> POS_LOADER
                Empty -> POS_EMPTY_VIEW
                Loaded -> POS_LIST
            }
        }

    private fun handleVMEvent(event: ArtifactListVMEvent) {
        when (event) {
            RetryLoading -> artifactListAdapter.retry()
            RefreshArtifacts -> artifactListAdapter.refresh()
            Close -> findNavController().navigateUp()
            is ShowDownloadFailed -> {
                showErrorSnack(event.error, R.string.artifact_list_download_queue_error)
            }
            ShowDownloadQueuedMessage -> showSnack(R.string.artifact_list_download_queued_successfully)
            ShowArtifactRemoved -> showSnack(R.string.artifact_list_download_artifact_removed)
            ShowDownloadNotSupported -> showSnack(R.string.artifact_list_download_not_supported)
            is ShowErrorLoadingArtifact -> {
                if (artifactListAdapter.itemCount == 0) {
                    binding.artifactListErrorView.setErrorThrowable(event.error.throwable)
                    binding.artifactListViewFlipper.displayedChild = POS_ERROR
                } else {
                    showErrorSnack(event.error)
                }
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

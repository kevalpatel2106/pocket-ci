package com.kevalpatel2106.feature.artifacts.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.extentions.isEmptyList
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateAdapter
import com.kevalpatel2106.feature.artifacts.R
import com.kevalpatel2106.feature.artifacts.databinding.FragmentArtifactListBinding
import com.kevalpatel2106.feature.artifacts.list.ArtifactListVMEvent.Close
import com.kevalpatel2106.feature.artifacts.list.ArtifactListVMEvent.RefreshArtifacts
import com.kevalpatel2106.feature.artifacts.list.ArtifactListVMEvent.RetryLoading
import com.kevalpatel2106.feature.artifacts.list.adapter.ArtifactAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ArtifactListFragment : Fragment(R.layout.fragment_artifact_list) {
    private val binding by viewBinding(FragmentArtifactListBinding::bind)
    private val viewModel by viewModels<ArtifactListViewModel>()
    private val artifactAdapter by lazy(LazyThreadSafetyMode.NONE) { ArtifactAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        prepareRecyclerView()
        observeAdapterLoadState()
        binding.artifactsSwipeRefresh.setOnRefreshListener(viewModel::reload)
        viewModel.pageViewState.collectInFragment(this, artifactAdapter::submitData)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleEvent)
    }

    private fun prepareRecyclerView() = with(binding.artifactsRecyclerView) {
        adapter = artifactAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    }

    private fun observeAdapterLoadState() = with(binding) {
        artifactAdapter.loadStateFlow.collectInFragment(this@ArtifactListFragment) { loadState ->
            artifactsSwipeRefresh.isRefreshing = false
            val sourceStates = loadState.source
            val refreshStates = loadState.refresh
            artifactsViewFlipper.displayedChild = when {
                refreshStates is LoadState.Error -> {
                    Timber.e(refreshStates.error)
                    artifactsErrorView.setErrorThrowable(refreshStates.error)
                    POS_ERROR
                }
                sourceStates.refresh is LoadState.Loading -> POS_LOADER
                sourceStates.isEmptyList(artifactAdapter) -> POS_EMPTY_VIEW
                else -> POS_LIST
            }
        }
    }

    private fun handleSingleEvent(event: ArtifactListVMEvent) {
        when (event) {
            RetryLoading -> artifactAdapter.retry()
            RefreshArtifacts -> artifactAdapter.refresh()
            Close -> findNavController().navigateUp()
        }
    }

    companion object {
        private const val POS_EMPTY_VIEW = 3
        private const val POS_ERROR = 2
        private const val POS_LIST = 1
        private const val POS_LOADER = 0
    }
}

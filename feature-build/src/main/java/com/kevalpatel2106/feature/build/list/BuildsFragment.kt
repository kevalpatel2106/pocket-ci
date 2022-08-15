package com.kevalpatel2106.feature.build.list

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
import com.kevalpatel2106.feature.build.R
import com.kevalpatel2106.feature.build.databinding.FragmentBuildsBinding
import com.kevalpatel2106.feature.build.list.BuildsVMEvent.Close
import com.kevalpatel2106.feature.build.list.BuildsVMEvent.OpenBuild
import com.kevalpatel2106.feature.build.list.BuildsVMEvent.RefreshBuilds
import com.kevalpatel2106.feature.build.list.BuildsVMEvent.RetryLoading
import com.kevalpatel2106.feature.build.list.adapter.BuildsAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
internal class BuildsFragment : Fragment(R.layout.fragment_builds) {
    private val viewModel by viewModels<BuildsViewModel>()
    private val binding by viewBinding(FragmentBuildsBinding::bind) {
        buildListRecyclerView.adapter = null
    }

    private val buildsAdapter by lazy(LazyThreadSafetyMode.NONE) { BuildsAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        prepareRecyclerView()
        observeAdapterLoadState()
        binding.buildListSwipeRefresh.setOnRefreshListener(viewModel::reload)
        viewModel.pageViewState.collectInFragment(this, buildsAdapter::submitData)
        viewModel.viewState.collectInFragment(this, ::handleViewState)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleEvent)
    }

    private fun prepareRecyclerView() = with(binding.buildListRecyclerView) {
        adapter = buildsAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    }

    private fun observeAdapterLoadState() = with(binding) {
        buildsAdapter.loadStateFlow.collectInFragment(this@BuildsFragment) { loadState ->
            buildListSwipeRefresh.isRefreshing = false
            val sourceStates = loadState.source
            val refreshStates = loadState.refresh
            buildsViewFlipper.displayedChild = when {
                refreshStates is LoadState.Error -> {
                    Timber.e(refreshStates.error)
                    buildListErrorView.setErrorThrowable(refreshStates.error)
                    POS_ERROR
                }
                sourceStates.refresh is LoadState.Loading -> POS_LOADER
                sourceStates.isEmptyList(buildsAdapter) -> POS_EMPTY_VIEW
                else -> POS_LIST
            }
        }
    }

    private fun handleViewState(viewState: BuildListViewState) {
        requireActivity().title = viewState.toolbarTitle
    }

    private fun handleSingleEvent(event: BuildsVMEvent) {
        when (event) {
            is OpenBuild -> findNavController().navigate(
                BuildsFragmentDirections.actionBuildsToDetail(event.accountId, event.build),
            )
            RetryLoading -> buildsAdapter.retry()
            RefreshBuilds -> buildsAdapter.refresh()
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

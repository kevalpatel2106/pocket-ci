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
import com.kevalpatel2106.feature.build.databinding.FragmentBuildListBinding
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.Close
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.OpenBuild
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.RefreshBuildList
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.RetryLoading
import com.kevalpatel2106.feature.build.list.adapter.BuildListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
internal class BuildListFragment : Fragment(R.layout.fragment_build_list) {
    private val viewModel by viewModels<BuildListViewModel>()
    private val binding by viewBinding(FragmentBuildListBinding::bind) {
        buildListRecyclerView.adapter = null
    }

    private val buildListAdapter by lazy(LazyThreadSafetyMode.NONE) { BuildListAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        prepareRecyclerView()
        observeAdapterLoadState()
        binding.buildListSwipeRefresh.setOnRefreshListener(viewModel::reload)
        viewModel.pageViewState.collectInFragment(this, buildListAdapter::submitData)
        viewModel.viewState.collectInFragment(this, ::handleViewState)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleEvent)
    }

    private fun prepareRecyclerView() = with(binding.buildListRecyclerView) {
        adapter = buildListAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    }

    private fun observeAdapterLoadState() = with(binding) {
        buildListAdapter.loadStateFlow.collectInFragment(this@BuildListFragment) { loadState ->
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
                sourceStates.isEmptyList(buildListAdapter) -> POS_EMPTY_VIEW
                else -> POS_LIST
            }
        }
    }

    private fun handleViewState(viewState: BuildListViewState) {
        requireActivity().title = viewState.toolbarTitle
    }

    private fun handleSingleEvent(event: BuildListVMEvent) {
        when (event) {
            is OpenBuild -> findNavController().navigate(
                BuildListFragmentDirections.actionBuildListToDetail(event.accountId, event.build),
            )
            RetryLoading -> buildListAdapter.retry()
            RefreshBuildList -> buildListAdapter.refresh()
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

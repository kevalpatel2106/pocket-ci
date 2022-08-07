package com.kevalpatel2106.builds.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.kevalpatel2106.builds.R
import com.kevalpatel2106.builds.databinding.FragmentBuildsBinding
import com.kevalpatel2106.builds.list.BuildsVMEvent.OpenBuild
import com.kevalpatel2106.builds.list.BuildsVMEvent.RefreshBuilds
import com.kevalpatel2106.builds.list.BuildsVMEvent.RetryLoading
import com.kevalpatel2106.builds.list.BuildsVMEvent.ShowErrorView
import com.kevalpatel2106.builds.list.adapter.BuildsAdapter
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.extentions.isEmptyList
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateAdapter
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleEvent)
    }

    private fun prepareRecyclerView() = with(binding.buildListRecyclerView) {
        adapter = buildsAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    }

    private fun observeAdapterLoadState() {
        buildsAdapter.loadStateFlow.collectInFragment(this) { loadState ->
            binding.buildListSwipeRefresh.isRefreshing = false
            val sourceStates = loadState.source
            val refreshStates = loadState.refresh
            binding.buildsViewFlipper.displayedChild = when {
                refreshStates is LoadState.Error -> POS_ERROR
                sourceStates.refresh is LoadState.Loading -> POS_LOADER
                sourceStates.isEmptyList(buildsAdapter) -> POS_EMPTY_VIEW
                else -> POS_LIST
            }
        }
    }

    private fun handleSingleEvent(event: BuildsVMEvent) {
        when (event) {
            is OpenBuild -> showSnack("Not implemented")
            RetryLoading -> buildsAdapter.retry()
            RefreshBuilds -> buildsAdapter.refresh()
            ShowErrorView -> binding.buildsViewFlipper.displayedChild = POS_ERROR
        }
    }

    companion object {
        private const val POS_EMPTY_VIEW = 3
        private const val POS_ERROR = 2
        private const val POS_LIST = 1
        private const val POS_LOADER = 0
    }
}

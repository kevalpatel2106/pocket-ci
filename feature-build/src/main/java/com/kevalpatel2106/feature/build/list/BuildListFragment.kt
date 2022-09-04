package com.kevalpatel2106.feature.build.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.paging.FirstPageLoadState.Empty
import com.kevalpatel2106.core.paging.FirstPageLoadState.Error
import com.kevalpatel2106.core.paging.FirstPageLoadState.Loaded
import com.kevalpatel2106.core.paging.FirstPageLoadState.Loading
import com.kevalpatel2106.core.paging.usecase.LoadStateMapper
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.coreViews.errorView.showErrorSnack
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateAdapter
import com.kevalpatel2106.feature.build.R
import com.kevalpatel2106.feature.build.databinding.FragmentBuildListBinding
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.Close
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.OpenBuild
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.RefreshBuildList
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.RetryLoading
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.ShowErrorLoadingBuilds
import com.kevalpatel2106.feature.build.list.adapter.BuildListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
internal class BuildListFragment : Fragment(R.layout.fragment_build_list) {
    private val viewModel by viewModels<BuildListViewModel>()
    private val binding by viewBinding(FragmentBuildListBinding::bind) {
        buildListRecyclerView.adapter = null
    }

    private val buildListAdapter by lazy(LazyThreadSafetyMode.NONE) { BuildListAdapter(viewModel) }

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

    private fun observeAdapterLoadState() = buildListAdapter.loadStateFlow
        .map { loadStateMapper(buildListAdapter, it) }
        .collectInFragment(this@BuildListFragment) { state ->
            binding.buildListSwipeRefresh.isRefreshing = false
            binding.buildsViewFlipper.displayedChild = when (state) {
                is Error -> {
                    binding.buildListErrorView.setErrorThrowable(state.error)
                    POS_ERROR
                }
                Loading -> POS_LOADER
                Empty -> POS_EMPTY_VIEW
                Loaded -> POS_LIST
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
            is ShowErrorLoadingBuilds -> {
                if (buildListAdapter.itemCount == 0) {
                    binding.buildListErrorView.setErrorThrowable(event.error.throwable)
                    binding.buildsViewFlipper.displayedChild = POS_ERROR
                } else {
                    showErrorSnack(event.error)
                }
            }
        }
    }

    companion object {
        private const val POS_EMPTY_VIEW = 3
        private const val POS_ERROR = 2
        private const val POS_LIST = 1
        private const val POS_LOADER = 0
    }
}

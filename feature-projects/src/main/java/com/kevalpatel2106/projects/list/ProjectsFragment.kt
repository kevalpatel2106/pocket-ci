package com.kevalpatel2106.projects.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.extentions.isEmptyList
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateAdapter
import com.kevalpatel2106.projects.R
import com.kevalpatel2106.projects.databinding.FragmentProjectsBinding
import com.kevalpatel2106.projects.list.ProjectVMEvent.OpenBuildsList
import com.kevalpatel2106.projects.list.ProjectVMEvent.RefreshProjects
import com.kevalpatel2106.projects.list.ProjectVMEvent.RetryLoading
import com.kevalpatel2106.projects.list.ProjectVMEvent.ShowErrorView
import com.kevalpatel2106.projects.list.adapter.ProjectAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectsFragment : Fragment(R.layout.fragment_projects) {
    private val viewModel by viewModels<ProjectsViewModel>()
    private val binding by viewBinding(FragmentProjectsBinding::bind) {
        projectListRecyclerView.adapter = null
    }

    private val projectAdapter by lazy(LazyThreadSafetyMode.NONE) { ProjectAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        prepareRecyclerView()
        observeAdapterLoadState()
        binding.projectListSwipeRefresh.setOnRefreshListener(viewModel::reload)
        viewModel.pageViewState.collectInFragment(this, projectAdapter::submitData)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleEvent)
    }

    private fun prepareRecyclerView() = with(binding.projectListRecyclerView) {
        adapter = projectAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    }

    private fun observeAdapterLoadState() {
        projectAdapter.loadStateFlow.collectInFragment(this) { loadState ->
            binding.projectListSwipeRefresh.isRefreshing = false
            val sourceStates = loadState.source
            val refreshStates = loadState.refresh
            binding.projectsViewFlipper.displayedChild = when {
                refreshStates is LoadState.Error -> POS_ERROR
                sourceStates.refresh is LoadState.Loading -> POS_LOADER
                sourceStates.isEmptyList(projectAdapter) -> POS_EMPTY_VIEW
                else -> POS_LIST
            }
        }
    }

    private fun handleSingleEvent(event: ProjectVMEvent) {
        when (event) {
            is OpenBuildsList -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.BuildsList(event.accountId, event.projectId),
            )
            RetryLoading -> projectAdapter.retry()
            RefreshProjects -> projectAdapter.refresh()
            ShowErrorView -> binding.projectsViewFlipper.displayedChild = POS_ERROR
        }
    }

    companion object {
        private const val POS_EMPTY_VIEW = 3
        private const val POS_ERROR = 2
        private const val POS_LIST = 1
        private const val POS_LOADER = 0
    }
}

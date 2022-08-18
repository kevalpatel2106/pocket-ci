package com.kevalpatel2106.feature.project.list

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
import com.kevalpatel2106.coreViews.errorView.showErrorSnack
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateAdapter
import com.kevalpatel2106.feature.project.R
import com.kevalpatel2106.feature.project.databinding.FragmentProjectListBinding
import com.kevalpatel2106.feature.project.list.ProjectListVMEvent.Close
import com.kevalpatel2106.feature.project.list.ProjectListVMEvent.OpenBuildsList
import com.kevalpatel2106.feature.project.list.ProjectListVMEvent.RefreshProjects
import com.kevalpatel2106.feature.project.list.ProjectListVMEvent.RetryLoading
import com.kevalpatel2106.feature.project.list.ProjectListVMEvent.ShowErrorLoadingProjects
import com.kevalpatel2106.feature.project.list.adapter.ProjectListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProjectListFragment : Fragment(R.layout.fragment_project_list) {
    private val viewModel by viewModels<ProjectListViewModel>()
    private val binding by viewBinding(FragmentProjectListBinding::bind) {
        projectListRecyclerView.adapter = null
    }

    private val projectListAdapter by lazy(LazyThreadSafetyMode.NONE) { ProjectListAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        prepareRecyclerView()
        observeAdapterLoadState()
        binding.projectListSwipeRefresh.setOnRefreshListener(viewModel::reload)
        viewModel.pageViewState.collectInFragment(this, projectListAdapter::submitData)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleEvent)
    }

    private fun prepareRecyclerView() = with(binding.projectListRecyclerView) {
        adapter = projectListAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    }

    private fun observeAdapterLoadState() = with(binding) {
        projectListAdapter.loadStateFlow.collectInFragment(this@ProjectListFragment) { loadState ->
            projectListSwipeRefresh.isRefreshing = false
            val sourceStates = loadState.source
            val refreshStates = loadState.refresh
            projectsViewFlipper.displayedChild = when {
                refreshStates is LoadState.Error -> {
                    Timber.e(refreshStates.error)
                    projectListErrorView.setErrorThrowable(refreshStates.error)
                    POS_ERROR
                }
                sourceStates.refresh is LoadState.Loading -> POS_LOADER
                sourceStates.isEmptyList(projectListAdapter) -> POS_EMPTY_VIEW
                else -> POS_LIST
            }
        }
    }

    private fun handleSingleEvent(event: ProjectListVMEvent) {
        when (event) {
            is OpenBuildsList -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.BuildList(event.accountId, event.projectId),
            )
            RetryLoading -> projectListAdapter.retry()
            RefreshProjects -> projectListAdapter.refresh()
            Close -> findNavController().navigateUp()
            is ShowErrorLoadingProjects -> {
                if (projectListAdapter.itemCount == 0) {
                    binding.projectListErrorView.setErrorThrowable(event.error.throwable)
                    binding.projectsViewFlipper.displayedChild = POS_ERROR
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

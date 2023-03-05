package com.kevalpatel2106.feature.project.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.kevalpatel2106.core.extentions.collectStateInFragment
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.paging.FirstPageLoadState.Empty
import com.kevalpatel2106.core.paging.FirstPageLoadState.Error
import com.kevalpatel2106.core.paging.FirstPageLoadState.Loaded
import com.kevalpatel2106.core.paging.FirstPageLoadState.Loading
import com.kevalpatel2106.core.paging.usecase.LoadStateMapper
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.core.baseUi.showErrorSnack
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
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class ProjectListFragment : Fragment(R.layout.fragment_project_list) {
    private val viewModel by viewModels<ProjectListViewModel>()
    private val binding by viewBinding(FragmentProjectListBinding::bind) {
        projectListRecyclerView.adapter = null
    }

    private val projectListAdapter by lazy(LazyThreadSafetyMode.NONE) { ProjectListAdapter(viewModel) }

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
        binding.projectListSwipeRefresh.setOnRefreshListener(viewModel::reload)
        viewModel.pageViewState.collectStateInFragment(this, projectListAdapter::submitData)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, ::handleSingleEvent)
    }

    private fun prepareRecyclerView() = with(binding.projectListRecyclerView) {
        adapter = projectListAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    }

    private fun observeAdapterLoadState() = projectListAdapter.loadStateFlow
        .map { loadStateMapper(projectListAdapter, it) }
        .collectStateInFragment(this) { state ->
            binding.projectListSwipeRefresh.isRefreshing = false
            binding.projectsViewFlipper.displayedChild = when (state) {
                is Error -> {
                    binding.projectListErrorView.setErrorThrowable(state.error)
                    POS_ERROR
                }
                Loading -> POS_LOADER
                Empty -> POS_EMPTY_VIEW
                Loaded -> POS_LIST
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

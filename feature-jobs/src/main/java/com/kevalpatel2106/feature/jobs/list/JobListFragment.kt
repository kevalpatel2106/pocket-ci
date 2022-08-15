package com.kevalpatel2106.feature.jobs.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.extentions.isEmptyList
import com.kevalpatel2106.core.navigation.DeepLinkDestinations.BuildLog
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateAdapter
import com.kevalpatel2106.feature.jobs.R
import com.kevalpatel2106.feature.jobs.databinding.FragmentJobListBinding
import com.kevalpatel2106.feature.jobs.list.JobListVMEvent.Close
import com.kevalpatel2106.feature.jobs.list.JobListVMEvent.OpenLogs
import com.kevalpatel2106.feature.jobs.list.JobListVMEvent.RefreshJobs
import com.kevalpatel2106.feature.jobs.list.JobListVMEvent.RetryLoading
import com.kevalpatel2106.feature.jobs.list.adapter.JobsAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class JobListFragment : Fragment(R.layout.fragment_job_list) {
    private val viewModel by viewModels<JobListViewModel>()
    private val binding by viewBinding(FragmentJobListBinding::bind) {
        jobListRecyclerView.adapter = null
    }
    private val jobsAdapter by lazy(LazyThreadSafetyMode.NONE) { JobsAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }
        prepareRecyclerView()
        observeAdapterLoadState()
        binding.jobListSwipeRefresh.setOnRefreshListener { viewModel.reload() }
        viewModel.pageViewState.collectInFragment(this, jobsAdapter::submitData)
        viewModel.viewState.collectInFragment(this, ::handleViewState)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleEvent)
    }

    private fun prepareRecyclerView() = with(binding.jobListRecyclerView) {
        adapter = jobsAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    }

    private fun handleViewState(viewState: JobListViewState) {
        viewState.toolbarTitle?.let { requireActivity().title = it }
    }

    private fun observeAdapterLoadState() = with(binding) {
        jobsAdapter.loadStateFlow.collectInFragment(this@JobListFragment) { loadState ->
            jobListSwipeRefresh.isRefreshing = false
            val sourceStates = loadState.source
            val refreshStates = loadState.refresh
            jobsViewFlipper.displayedChild = when {
                refreshStates is LoadState.Error -> {
                    Timber.e(refreshStates.error)
                    jobListErrorView.setErrorThrowable(refreshStates.error)
                    POS_ERROR
                }
                sourceStates.refresh is LoadState.Loading -> POS_LOADER
                sourceStates.isEmptyList(jobsAdapter) -> POS_EMPTY_VIEW
                else -> POS_LIST
            }
        }
    }

    private fun handleSingleEvent(event: JobListVMEvent) {
        when (event) {
            is OpenLogs -> findNavController().navigateToInAppDeeplink(
                BuildLog(event.accountId, event.projectId, event.buildId, event.jobId),
            )
            RetryLoading -> jobsAdapter.retry()
            RefreshJobs -> jobsAdapter.refresh()
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

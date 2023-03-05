package com.kevalpatel2106.feature.job.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.kevalpatel2106.core.extentions.collectStateInFragment
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.core.navigation.DeepLinkDestinations.BuildLog
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.paging.FirstPageLoadState.Empty
import com.kevalpatel2106.core.paging.FirstPageLoadState.Error
import com.kevalpatel2106.core.paging.FirstPageLoadState.Loaded
import com.kevalpatel2106.core.paging.FirstPageLoadState.Loading
import com.kevalpatel2106.core.paging.usecase.LoadStateMapper
import com.kevalpatel2106.core.viewbinding.viewBinding
import com.kevalpatel2106.core.baseUi.showErrorSnack
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateAdapter
import com.kevalpatel2106.feature.job.R
import com.kevalpatel2106.feature.job.databinding.FragmentJobListBinding
import com.kevalpatel2106.feature.job.list.JobListVMEvent.Close
import com.kevalpatel2106.feature.job.list.JobListVMEvent.OpenLogs
import com.kevalpatel2106.feature.job.list.JobListVMEvent.RefreshJobs
import com.kevalpatel2106.feature.job.list.JobListVMEvent.RetryLoading
import com.kevalpatel2106.feature.job.list.JobListVMEvent.ShowErrorLoadingJobs
import com.kevalpatel2106.feature.job.list.adapter.JobsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class JobListFragment : Fragment(R.layout.fragment_job_list) {
    private val viewModel by viewModels<JobListViewModel>()
    private val binding by viewBinding(FragmentJobListBinding::bind) {
        jobListRecyclerView.adapter = null
    }
    private val jobsListAdapter by lazy(LazyThreadSafetyMode.NONE) { JobsListAdapter(viewModel) }

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
        binding.jobListSwipeRefresh.setOnRefreshListener { viewModel.reload() }
        viewModel.pageViewState.collectStateInFragment(this, jobsListAdapter::submitData)
        viewModel.viewState.collectStateInFragment(this, ::handleViewState)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, ::handleSingleEvent)
    }

    private fun prepareRecyclerView() = with(binding.jobListRecyclerView) {
        adapter = jobsListAdapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter(viewModel),
            footer = NetworkStateAdapter(viewModel),
        )
        itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
    }

    private fun handleViewState(viewState: JobListViewState) {
        viewState.toolbarTitle?.let { requireActivity().title = it }
    }

    private fun observeAdapterLoadState() = jobsListAdapter.loadStateFlow
        .map { loadStateMapper(jobsListAdapter, it) }
        .collectStateInFragment(this) { state ->
            binding.jobListSwipeRefresh.isRefreshing = false
            binding.jobListViewFlipper.displayedChild = when (state) {
                is Error -> {
                    binding.jobListErrorView.setErrorThrowable(state.error)
                    POS_ERROR
                }
                Loading -> POS_LOADER
                Empty -> POS_EMPTY_VIEW
                Loaded -> POS_LIST
            }
        }

    private fun handleSingleEvent(event: JobListVMEvent) {
        when (event) {
            is OpenLogs -> findNavController().navigateToInAppDeeplink(
                BuildLog(event.accountId, event.projectId, event.buildId, event.jobId),
            )
            RetryLoading -> jobsListAdapter.retry()
            RefreshJobs -> jobsListAdapter.refresh()
            Close -> findNavController().navigateUp()
            is ShowErrorLoadingJobs -> {
                if (jobsListAdapter.itemCount == 0) {
                    binding.jobListErrorView.setErrorThrowable(event.displayError.throwable)
                    binding.jobListViewFlipper.displayedChild = POS_ERROR
                } else {
                    showErrorSnack(event.displayError)
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

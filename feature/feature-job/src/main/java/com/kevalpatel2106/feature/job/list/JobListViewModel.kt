package com.kevalpatel2106.feature.job.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.usecase.SecondsTicker
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.feature.job.list.model.JobListItem
import com.kevalpatel2106.feature.job.list.model.JobListVMEvent
import com.kevalpatel2106.feature.job.list.model.JobListVMEvent.Close
import com.kevalpatel2106.feature.job.list.model.JobListVMEvent.OpenLogs
import com.kevalpatel2106.feature.job.list.model.JobListVMEvent.ShowErrorLoadingJobs
import com.kevalpatel2106.feature.job.list.model.JobListViewState
import com.kevalpatel2106.feature.job.list.usecase.JobItemMapper
import com.kevalpatel2106.repository.CIInfoRepo
import com.kevalpatel2106.repository.JobRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class JobListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    jobRepo: JobRepo,
    private val ciInfoRepo: CIInfoRepo,
    private val jobItemMapper: JobItemMapper,
    private val displayErrorMapper: DisplayErrorMapper,
    private val secondsTicker: SecondsTicker,
) : ViewModel() {
    private val navArgs = JobListFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _vmEventsFlow = MutableSharedFlow<JobListVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    private val _viewState = MutableStateFlow(JobListViewState.initialState(navArgs))
    val viewState = _viewState.asStateFlow()

    val pageViewState: Flow<PagingData<JobListItem>> = jobRepo
        .getJobs(
            navArgs.accountId.toAccountId(),
            navArgs.projectId.toProjectId(),
            navArgs.buildId.toBuildId(),
        )
        .cachedIn(viewModelScope)
        .catch { error ->
            Timber.e(error)
            _vmEventsFlow.emit(ShowErrorLoadingJobs(displayErrorMapper(error)))
        }
        .flowOn(Dispatchers.Default)
        .flatMapLatest { pagedData -> secondsTicker().map { pagedData } }
        .map { pagedData ->
            pagedData.map { build ->
                @Suppress("USELESS_CAST")
                jobItemMapper(build) as JobListItem
            }
        }

    fun onJobSelected(job: Job) {
        viewModelScope.launch {
            val ciInfo = ciInfoRepo.getCIInfo(navArgs.accountId.toAccountId())
            if (ciInfo.supportJobLevelLogs) {
                _vmEventsFlow.emit(
                    OpenLogs(
                        accountId = navArgs.accountId.toAccountId(),
                        projectId = navArgs.projectId.toProjectId(),
                        buildId = navArgs.buildId.toBuildId(),
                        jobId = job.id,
                    ),
                )
            }
        }
    }

    fun close() {
        viewModelScope.launch { _vmEventsFlow.emit(Close) }
    }
}

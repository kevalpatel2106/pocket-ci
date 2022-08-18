package com.kevalpatel2106.feature.build.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.modify
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateCallback
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.Close
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.OpenBuild
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.RefreshBuildList
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.RetryLoading
import com.kevalpatel2106.feature.build.list.BuildListVMEvent.ShowErrorLoadingBuilds
import com.kevalpatel2106.feature.build.list.adapter.BuildListAdapterCallback
import com.kevalpatel2106.feature.build.list.adapter.BuildListItem
import com.kevalpatel2106.feature.build.list.usecase.BuildItemMapper
import com.kevalpatel2106.repository.BuildRepo
import com.kevalpatel2106.repository.ProjectRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class BuildListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    projectRepo: ProjectRepo,
    buildRepo: BuildRepo,
    displayErrorMapper: DisplayErrorMapper,
    buildItemMapper: BuildItemMapper,
) : BaseViewModel<BuildListVMEvent>(), NetworkStateCallback, BuildListAdapterCallback {
    private val navArgs = BuildListFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _viewState = MutableStateFlow(BuildListViewState.initialState())
    val viewState = _viewState.asStateFlow()

    val pageViewState: Flow<PagingData<BuildListItem>> = buildRepo
        .getBuilds(navArgs.accountId.toAccountId(), navArgs.projectId.toProjectId())
        .map { pagedData ->
            pagedData.map { build ->
                @Suppress("USELESS_CAST")
                buildItemMapper(build) as BuildListItem
            }
        }
        .catch { error ->
            Timber.e(error)
            _vmEventsFlow.emit(ShowErrorLoadingBuilds(displayErrorMapper(error)))
        }
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            runCatching {
                projectRepo.getProject(
                    navArgs.projectId.toProjectId(),
                    navArgs.accountId.toAccountId(),
                )
            }.onSuccess { project ->
                _viewState.modify { copy(toolbarTitle = project?.fullName.orEmpty()) }
            }.onFailure { error ->
                // Ignore error. Just log it.
                Timber.e(error)
            }
        }
    }

    override fun onBuildSelected(build: Build) {
        viewModelScope.launch {
            _vmEventsFlow.emit(OpenBuild(navArgs.accountId.toAccountId(), build))
        }
    }

    fun reload() {
        viewModelScope.launch { _vmEventsFlow.emit(RefreshBuildList) }
    }

    override fun close() {
        viewModelScope.launch { _vmEventsFlow.emit(Close) }
    }

    override fun retryNextPage() {
        viewModelScope.launch { _vmEventsFlow.emit(RetryLoading) }
    }
}

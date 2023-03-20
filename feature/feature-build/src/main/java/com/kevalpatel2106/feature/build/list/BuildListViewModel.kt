package com.kevalpatel2106.feature.build.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.usecase.SecondsTicker
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.feature.build.list.model.BuildListItem
import com.kevalpatel2106.feature.build.list.model.BuildListVMEvent
import com.kevalpatel2106.feature.build.list.model.BuildListVMEvent.Close
import com.kevalpatel2106.feature.build.list.model.BuildListVMEvent.OpenBuild
import com.kevalpatel2106.feature.build.list.model.BuildListVMEvent.ShowErrorLoadingBuilds
import com.kevalpatel2106.feature.build.list.model.BuildListViewState
import com.kevalpatel2106.feature.build.list.usecase.BuildItemMapper
import com.kevalpatel2106.repository.BuildRepo
import com.kevalpatel2106.repository.ProjectRepo
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
import kotlinx.coroutines.flow.update
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
    secondsTicker: SecondsTicker,
) : ViewModel() {
    private val navArgs = BuildListFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _vmEventsFlow = MutableSharedFlow<BuildListVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    private val _viewState = MutableStateFlow(BuildListViewState.initialState())
    val viewState = _viewState.asStateFlow()

    val pageViewState: Flow<PagingData<BuildListItem>> = buildRepo
        .getBuilds(navArgs.accountId.toAccountId(), navArgs.projectId.toProjectId())
        .cachedIn(viewModelScope)
        .flowOn(Dispatchers.Default)
        .flatMapLatest { pagedData -> secondsTicker().map { pagedData } }
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

    init {
        loadProjectInfo(projectRepo)
    }

    private fun loadProjectInfo(projectRepo: ProjectRepo) {
        viewModelScope.launch {
            runCatching {
                projectRepo.getProject(
                    navArgs.projectId.toProjectId(),
                    navArgs.accountId.toAccountId(),
                )
            }.onSuccess { project ->
                _viewState.update { it.copy(toolbarTitle = project?.fullName.orEmpty()) }
            }.onFailure { error ->
                // Ignore error. Just log it.
                Timber.e(error)
            }
        }
    }

    fun onBuildSelected(item: BuildListItem.BuildItem) {
        viewModelScope.launch {
            _vmEventsFlow.emit(OpenBuild(navArgs.accountId.toAccountId(), item.build))
        }
    }

    fun close() {
        viewModelScope.launch { _vmEventsFlow.emit(Close) }
    }
}

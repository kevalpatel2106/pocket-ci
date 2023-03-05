package com.kevalpatel2106.feature.project.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateCallback
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.feature.project.list.ProjectListVMEvent.Close
import com.kevalpatel2106.feature.project.list.ProjectListVMEvent.OpenBuildsList
import com.kevalpatel2106.feature.project.list.ProjectListVMEvent.RefreshProjects
import com.kevalpatel2106.feature.project.list.ProjectListVMEvent.RetryLoading
import com.kevalpatel2106.feature.project.list.ProjectListVMEvent.ShowErrorLoadingProjects
import com.kevalpatel2106.feature.project.list.adapter.ProjectListAdapterCallback
import com.kevalpatel2106.feature.project.list.adapter.ProjectListItem
import com.kevalpatel2106.feature.project.list.adapter.ProjectListItem.ProjectItem
import com.kevalpatel2106.feature.project.list.usecase.InsertProjectListHeaders
import com.kevalpatel2106.repository.ProjectRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ProjectListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val projectRepo: ProjectRepo,
    insertListSeparator: InsertProjectListHeaders,
    displayErrorMapper: DisplayErrorMapper,
) : ViewModel(), ProjectListAdapterCallback, NetworkStateCallback {
    private val navArgs = ProjectListFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _vmEventsFlow = MutableSharedFlow<ProjectListVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    val pageViewState: Flow<PagingData<ProjectListItem>> = projectRepo
        .getProjects(navArgs.accountId.toAccountId())
        .mapNotNull { pagingData ->
            pagingData.map { project -> ProjectItem(project) }
        }
        .mapNotNull { pagingData ->
            pagingData.insertSeparators { before, after ->
                insertListSeparator(before?.project, after?.project)
            }
        }
        .catch { error ->
            Timber.e(error)
            _vmEventsFlow.emit(ShowErrorLoadingProjects(displayErrorMapper(error)))
        }
        .cachedIn(viewModelScope)

    override fun onProjectSelected(project: Project) {
        viewModelScope.launch {
            _vmEventsFlow.emit(OpenBuildsList(project.accountId, project.remoteId))
        }
    }

    override fun togglePin(project: Project, isChecked: Boolean) {
        if (project.isPinned == isChecked) return
        viewModelScope.launch {
            if (project.isPinned) {
                projectRepo.unpinProject(project.remoteId, project.accountId)
            } else {
                projectRepo.pinProject(project.remoteId, project.accountId)
            }
        }
    }

    fun reload() = viewModelScope.launch { _vmEventsFlow.emit(RefreshProjects) }

    override fun close() {
        viewModelScope.launch { _vmEventsFlow.emit(Close) }
    }

    override fun retryNextPage() {
        viewModelScope.launch { _vmEventsFlow.emit(RetryLoading) }
    }
}

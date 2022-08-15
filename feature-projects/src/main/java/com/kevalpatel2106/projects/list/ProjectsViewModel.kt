package com.kevalpatel2106.projects.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateCallback
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.projects.list.ProjectVMEvent.Close
import com.kevalpatel2106.projects.list.ProjectVMEvent.OpenBuildsList
import com.kevalpatel2106.projects.list.ProjectVMEvent.RefreshProjects
import com.kevalpatel2106.projects.list.ProjectVMEvent.RetryLoading
import com.kevalpatel2106.projects.list.adapter.ProjectAdapterCallback
import com.kevalpatel2106.projects.list.adapter.ProjectListItem
import com.kevalpatel2106.projects.list.adapter.ProjectListItem.ProjectItem
import com.kevalpatel2106.projects.list.usecase.InsertProjectListHeaders
import com.kevalpatel2106.repository.ProjectRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProjectsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    projectRepo: ProjectRepo,
    insertListSeparator: InsertProjectListHeaders,
) : BaseViewModel<ProjectVMEvent>(), ProjectAdapterCallback, NetworkStateCallback {
    private val navArgs = ProjectsFragmentArgs.fromSavedStateHandle(savedStateHandle)

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
        .cachedIn(viewModelScope)

    override fun onProjectSelected(project: Project) {
        viewModelScope.launch {
            _vmEventsFlow.emit(OpenBuildsList(project.accountId, project.remoteId))
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

package com.kevalpatel2106.builds.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kevalpatel2106.builds.list.BuildsVMEvent.OpenBuild
import com.kevalpatel2106.builds.list.BuildsVMEvent.RefreshBuilds
import com.kevalpatel2106.builds.list.BuildsVMEvent.RetryLoading
import com.kevalpatel2106.builds.list.adapter.BuildListItem
import com.kevalpatel2106.builds.list.adapter.BuildsAdapterCallback
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateCallback
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.repository.BuildRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BuildsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    buildRepo: BuildRepo,
) : BaseViewModel<BuildsVMEvent>(), NetworkStateCallback, BuildsAdapterCallback {
    private val navArgs = BuildsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    val pageViewState: Flow<PagingData<BuildListItem>> = buildRepo
        .getBuilds(navArgs.accountId.toAccountId(), navArgs.projectId.toProjectId())
        .map { pagedData ->
            pagedData.map { build ->
                @Suppress("USELESS_CAST")
                BuildListItem.BuildItem(build) as BuildListItem
            }
        }
        .cachedIn(viewModelScope)

    override fun onBuildSelected(build: Build) {
        viewModelScope.launch { _vmEventsFlow.emit(OpenBuild(build.id)) }
    }

    fun reload() {
        viewModelScope.launch { _vmEventsFlow.emit(RefreshBuilds) }
    }

    override fun retryNextPage() {
        viewModelScope.launch { _vmEventsFlow.emit(RetryLoading) }
    }
}

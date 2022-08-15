package com.kevalpatel2106.feature.build.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.extentions.modify
import com.kevalpatel2106.feature.build.R
import com.kevalpatel2106.feature.build.detail.BuildDetailVMEvent.OpenBuildLogs
import com.kevalpatel2106.feature.build.detail.BuildDetailVMEvent.OpenJobs
import com.kevalpatel2106.feature.build.detail.BuildDetailVMEvent.OpenMarkDownViewer
import com.kevalpatel2106.feature.build.detail.BuildDetailVMEvent.ShowErrorAndClose
import com.kevalpatel2106.repository.AccountRepo
import com.kevalpatel2106.repository.CIInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class BuildDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    ciInfoRepo: CIInfoRepo,
    accountRepo: AccountRepo,
) : BaseViewModel<BuildDetailVMEvent>() {
    private val navArgs = BuildDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _viewState = MutableStateFlow(BuildDetailViewState.initialState(navArgs.build))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching {
                val account = accountRepo.getAccount(navArgs.accountId)
                ciInfoRepo.getCI(account.type)
            }.onSuccess {
                _viewState.modify {
                    copy(
                        hideBuildLogButton = !it.supportBuildLogs,
                        hideJobsListButton = !it.supportJobs,
                    )
                }
            }.onFailure { error ->
                Timber.e(error)
                _vmEventsFlow.emit(ShowErrorAndClose(error))
            }
        }
    }

    fun onOpenBuildLogs() = viewModelScope.launch {
        _vmEventsFlow.emit(
            OpenBuildLogs(
                accountId = navArgs.accountId,
                projectId = navArgs.build.projectId,
                buildId = navArgs.build.id,
            ),
        )
    }

    fun onOpenJobs() = viewModelScope.launch {
        _vmEventsFlow.emit(
            OpenJobs(
                accountId = navArgs.accountId,
                projectId = navArgs.build.projectId,
                buildId = navArgs.build.id,
                title = "${viewState.value.build.workflow.name} (#${viewState.value.build.number})",
            ),
        )
    }

    fun onViewCommitMessageClicked() = viewModelScope.launch {
        val commitMsg = navArgs.build.commit?.message
        requireNotNull(commitMsg) { "Commit message is null. Cannot open full screen view." }
        _vmEventsFlow.emit(
            OpenMarkDownViewer(
                titleRes = R.string.build_detail_commit_markdown_view_title,
                commitMessage = commitMsg,
            ),
        )
    }

    fun onViewAbortMessageClicked() = viewModelScope.launch {
        val abortMessage = navArgs.build.abortReason
        requireNotNull(abortMessage) { "Abort reason is null. Cannot open full screen view." }
        _vmEventsFlow.emit(
            OpenMarkDownViewer(
                titleRes = R.string.build_detail_commit_markdown_view_title,
                commitMessage = abortMessage,
            ),
        )
    }
}

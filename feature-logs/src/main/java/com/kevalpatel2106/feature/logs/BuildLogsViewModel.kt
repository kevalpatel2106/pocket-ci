package com.kevalpatel2106.feature.logs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.extentions.modify
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.id.toJobIdOrNull
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.feature.logs.BuildLogsVMEvent.Close
import com.kevalpatel2106.feature.logs.BuildLogsVMEvent.ScrollToBottom
import com.kevalpatel2106.feature.logs.BuildLogsVMEvent.ScrollToTop
import com.kevalpatel2106.feature.logs.BuildLogsViewState.Empty
import com.kevalpatel2106.feature.logs.BuildLogsViewState.Error
import com.kevalpatel2106.feature.logs.BuildLogsViewState.Success
import com.kevalpatel2106.feature.logs.usecase.CalculateTextScale
import com.kevalpatel2106.feature.logs.usecase.CalculateTextScale.Companion.DEFAULT_SCALE
import com.kevalpatel2106.feature.logs.usecase.LogSourceSelector
import com.kevalpatel2106.feature.logs.usecase.TextChangeDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class BuildLogsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val logSourceSelector: LogSourceSelector,
    private val calculateTextScale: CalculateTextScale,
) : BaseViewModel<BuildLogsVMEvent>() {
    private val navArgs = BuildLogsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _viewState = MutableStateFlow<BuildLogsViewState>(BuildLogsViewState.initialState())
    val viewState = _viewState.asStateFlow()

    init {
        downloadLogs()
    }

    private fun downloadLogs() {
        viewModelScope.launch {
            _viewState.emit(BuildLogsViewState.Loading)
            runCatching {
                logSourceSelector(
                    accountId = navArgs.accountId.toAccountId(),
                    projectId = navArgs.projectId.toProjectId(),
                    buildId = navArgs.buildId.toBuildId(),
                    jobId = navArgs.jobId.toJobIdOrNull(),
                )
            }.onSuccess { logs ->
                val paddedLogs = "\n\n$logs\n\n\n\n"
                if (logs.isBlank()) {
                    _viewState.emit(Empty)
                } else {
                    _viewState.emit(Success(paddedLogs, DEFAULT_SCALE))
                    _vmEventsFlow.emit(ScrollToTop)
                }
            }.onFailure { error ->
                Timber.e(error)
                _viewState.emit(Error(error))
            }
        }
    }

    fun reload() = downloadLogs()

    fun scrollToBottom() = viewModelScope.launch { _vmEventsFlow.emit(ScrollToBottom) }

    fun close() = viewModelScope.launch { _vmEventsFlow.emit(Close) }

    fun scrollToTop() = viewModelScope.launch { _vmEventsFlow.emit(ScrollToTop) }

    fun onTextSizeChanged(@TextChangeDirection direction: Int) = _viewState.modify(viewModelScope) {
        if (this is Success) {
            copy(textScale = calculateTextScale(direction))
        } else {
            this
        }
    }
}

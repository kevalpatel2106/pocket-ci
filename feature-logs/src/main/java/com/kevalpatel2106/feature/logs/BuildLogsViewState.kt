package com.kevalpatel2106.feature.logs

internal sealed class BuildLogsViewState(val viewFlipperPos: Int) {
    data class Success(val logs: String, val textScale: Float) : BuildLogsViewState(POS_LOGS)
    object Loading : BuildLogsViewState(POS_LOADER)
    data class Error(val error: Throwable) : BuildLogsViewState(POS_ERROR)
    object Empty : BuildLogsViewState(POS_EMPTY_VIEW)

    companion object {
        private const val POS_EMPTY_VIEW = 3
        private const val POS_ERROR = 2
        private const val POS_LOGS = 1
        private const val POS_LOADER = 0
        fun initialState() = Loading
    }
}

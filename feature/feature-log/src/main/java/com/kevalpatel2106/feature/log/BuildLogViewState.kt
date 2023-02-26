package com.kevalpatel2106.feature.log

internal sealed class BuildLogViewState(val viewFlipperPos: Int) {
    data class Success(val logs: String, val textScale: Float) : BuildLogViewState(POS_LOGS)
    object Loading : BuildLogViewState(POS_LOADER)
    data class Error(val error: Throwable) : BuildLogViewState(POS_ERROR)
    object Empty : BuildLogViewState(POS_EMPTY_VIEW)

    companion object {
        private const val POS_EMPTY_VIEW = 3
        private const val POS_ERROR = 2
        private const val POS_LOGS = 1
        private const val POS_LOADER = 0
        fun initialState() = Loading
    }
}

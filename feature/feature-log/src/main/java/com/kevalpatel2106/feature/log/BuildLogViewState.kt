package com.kevalpatel2106.feature.log

import com.kevalpatel2106.entity.DisplayError
import kotlinx.collections.immutable.PersistentList

internal sealed class BuildLogViewState {
    data class Success(val logs: PersistentList<String>, val textScale: Float) : BuildLogViewState()
    object Loading : BuildLogViewState()
    data class Error(val error: DisplayError) : BuildLogViewState()
    object Empty : BuildLogViewState()

    companion object {
        fun initialState() = Loading
    }
}

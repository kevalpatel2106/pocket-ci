package com.kevalpatel2106.registration.ciSelection

import com.kevalpatel2106.entity.CIInfo

internal sealed class CISelectionViewState(val flipperPosition: Int) {
    data class SuccessState(val listOfCi: List<CIInfo>) : CISelectionViewState(POS_LIST)
    object LoadingState : CISelectionViewState(POS_LOADER)
    data class ErrorState(val error: Throwable) : CISelectionViewState(POS_ERROR)

    companion object {
        const val POS_ERROR = 2
        const val POS_LIST = 1
        const val POS_LOADER = 0

        @JvmStatic
        fun initialState() = LoadingState
    }
}

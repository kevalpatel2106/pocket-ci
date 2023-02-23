package com.kevalpatel2106.registration.ciSelection

import com.kevalpatel2106.entity.CIInfo

internal sealed class CISelectionViewState {
    data class SuccessState(val listOfCi: List<CIInfo>) : CISelectionViewState()
    object LoadingState : CISelectionViewState()
    data class ErrorState(val error: Throwable) : CISelectionViewState()

    companion object {
        @JvmStatic
        fun initialState() = LoadingState
    }
}

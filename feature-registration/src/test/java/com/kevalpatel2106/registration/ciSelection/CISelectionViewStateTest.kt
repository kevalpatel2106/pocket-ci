package com.kevalpatel2106.registration.ciSelection

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CISelectionViewStateTest {

    @Test
    fun `check initial view state`() {
        assertEquals(CISelectionViewState.initialState(), CISelectionViewState.LoadingState)
    }

    @Test
    fun `check flipper position for loading view state`() {
        assertEquals(
            CISelectionViewState.POS_LOADER,
            CISelectionViewState.LoadingState.flipperPosition,
        )
    }

    @Test
    fun `check flipper position for success view state`() {
        assertEquals(
            CISelectionViewState.POS_LIST,
            CISelectionViewState.SuccessState(listOf()).flipperPosition,
        )
    }

    @Test
    fun `check flipper position for error view state`() {
        assertEquals(
            CISelectionViewState.POS_ERROR,
            CISelectionViewState.ErrorState(Throwable()).flipperPosition,
        )
    }
}

package com.kevalpatel2106.registration.ciSelection.model

import com.kevalpatel2106.registration.ciSelection.model.CISelectionViewState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CISelectionViewStateTest {

    @Test
    fun `check initial view state`() {
        assertEquals(CISelectionViewState.initialState(), CISelectionViewState.LoadingState)
    }
}

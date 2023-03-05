package com.kevalpatel2106.feature.log

import com.kevalpatel2106.feature.log.BuildLogViewState.Loading
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BuildLogViewStateTest {

    @Test
    fun `check initial state is loading`() {
        assertEquals(Loading, BuildLogViewState.initialState())
    }
}

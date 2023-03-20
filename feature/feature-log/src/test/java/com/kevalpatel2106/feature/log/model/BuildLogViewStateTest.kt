package com.kevalpatel2106.feature.log.model

import com.kevalpatel2106.feature.log.log.model.BuildLogViewState
import com.kevalpatel2106.feature.log.log.model.BuildLogViewState.Loading
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BuildLogViewStateTest {

    @Test
    fun `check initial state is loading`() {
        assertEquals(Loading, BuildLogViewState.initialState())
    }
}

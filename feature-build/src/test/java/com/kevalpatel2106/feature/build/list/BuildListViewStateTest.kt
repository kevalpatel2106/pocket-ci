package com.kevalpatel2106.feature.build.list

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BuildListViewStateTest {

    @Test
    fun `check title is empty in initial state`() {
        val actual = BuildListViewState.initialState().toolbarTitle

        assertTrue(actual.isEmpty())
    }
}

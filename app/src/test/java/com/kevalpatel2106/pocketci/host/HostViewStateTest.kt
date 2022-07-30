package com.kevalpatel2106.pocketci.host

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class HostViewStateTest {

    @Test
    fun `when loading initial view state then check navigation icon is not visible`() {
        val result = HostViewState.initialState()

        assertFalse(result.navigationIconVisible)
    }

    @Test
    fun `when loading initial view state then check navigation icon is null`() {
        val result = HostViewState.initialState()

        assertNull(result.navigationIcon)
    }
}

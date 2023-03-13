package com.kevalpatel2106.feature.splash.model

import com.kevalpatel2106.entity.NightMode
import com.kevalpatel2106.feature.splash.splash.model.SplashViewState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SplashViewStateTest {

    @Test
    fun `when loading initial states then night mode is set to auto`() {
        val result = SplashViewState.initialState()

        assertEquals(NightMode.AUTO, result.nightMode)
    }
}

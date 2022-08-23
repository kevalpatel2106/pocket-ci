package com.kevalpatel2106.core.databinding

import android.view.View
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class BindingConvertersTest {

    @ParameterizedTest(name = "given is not visible {0} when converted to visibility the check visibility is {1}")
    @MethodSource("provideValuesForBooleanToVisibilityTest")
    fun `given is not visible when converted to visibility the check visibility`(
        isNotVisible: Boolean,
        expectedVisibility: Int,
    ) {
        assertEquals(expectedVisibility, BindingConverters.booleanToVisibility(isNotVisible))
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValuesForBooleanToVisibilityTest() = listOf(
            // Format: is not visible, expected visibility
            arguments(true, View.GONE),
            arguments(false, View.VISIBLE),
        )
    }
}

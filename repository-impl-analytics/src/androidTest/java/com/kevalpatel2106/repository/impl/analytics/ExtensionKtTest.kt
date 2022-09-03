package com.kevalpatel2106.repository.impl.analytics

import androidx.core.os.bundleOf
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ExtensionKtTest {

    @Test
    fun given_map_when_converted_to_bundle_then_check_bundle() {
        val map = mapOf("key" to "value")

        val actual = map.toBundle()

        assertEquals(bundleOf("key" to "value"), actual)
    }

    @Test
    fun given_empty_map_when_converted_to_bundle_then_check_empty_bundle() {
        val map = emptyMap<String, String?>()

        val actual = map.toBundle()

        assertEquals(bundleOf(), actual)
    }
}

package com.kevalpatel2106.feature.job.list

import com.flextrade.kfixture.KFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class JobListViewStateTest {
    private val fixture = KFixture()
    private val navArgs = fixture<JobListFragmentArgs>()

    @Test
    fun `check initial state`() {
        val actual = JobListViewState.initialState(navArgs)

        val expected = JobListViewState(navArgs.title)
        assertEquals(expected, actual)
    }
}

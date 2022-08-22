package com.kevalpatel2106.feature.job.list.adapter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getJobFixture
import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.feature.job.getJobItemFixture
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.Date

internal class JobListDiffCallbackTest {

    @ParameterizedTest(name = "given old {0} and new {1} item when checking are contents same then return {2}")
    @MethodSource("provideAreContentSame")
    fun `given old and new item when checking are contents same then return expected value`(
        oldItem: JobListItem,
        newItem: JobListItem,
        expectedAreContentSame: Boolean,
    ) {
        val actual = JobListDiffCallback.areContentsTheSame(oldItem, newItem)

        Assertions.assertEquals(expectedAreContentSame, actual)
    }

    @ParameterizedTest(name = "given old {0} and new {1} item when checking are items same then return {2}")
    @MethodSource("provideAreItemSame")
    fun `given old and new item when checking are items same then return expected value`(
        oldItem: JobListItem,
        newItem: JobListItem,
        expectedAreItemSame: Boolean,
    ) {
        val actual = JobListDiffCallback.areItemsTheSame(oldItem, newItem)

        Assertions.assertEquals(expectedAreItemSame, actual)
    }

    companion object {
        private val fixture = KFixture()
        private val job = getJobFixture(fixture)
        private val jobItem = getJobItemFixture(fixture).copy(job = job)

        @Suppress("unused")
        @JvmStatic
        fun provideAreItemSame() = listOf(
            // Format old item, new item, expected are content same
            arguments(jobItem, jobItem.copy(), true),
            arguments(
                jobItem.copy(job = job.copy(id = JobId("2"))),
                jobItem.copy(job = job.copy(id = JobId("3"))),
                false,
            ),
            arguments(
                jobItem.copy(
                    triggeredTimeDifference = fixture<TimeDifferenceData>()
                        .copy(dateOfEventEnd = null),
                ),
                jobItem.copy(
                    triggeredTimeDifference = fixture<TimeDifferenceData>()
                        .copy(dateOfEventEnd = Date(System.currentTimeMillis())),
                ),
                true,
            ),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideAreContentSame() = listOf(
            // Format old item, new item, expected are item same
            arguments(jobItem, jobItem.copy(), true),
            arguments(
                jobItem.copy(job = job.copy(id = JobId("2"))),
                jobItem.copy(job = job.copy(id = JobId("3"))),
                false,
            ),
            arguments(
                jobItem.copy(
                    executionTimeDifference = fixture<TimeDifferenceData>()
                        .copy(dateOfEventEnd = null),
                ),
                jobItem.copy(
                    executionTimeDifference = fixture<TimeDifferenceData>()
                        .copy(dateOfEventEnd = Date(System.currentTimeMillis())),
                ),
                false,
            ),
        )
    }
}

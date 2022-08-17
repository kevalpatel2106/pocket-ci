package com.kevalpatel2106.feature.project.list.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.feature.project.R
import com.kevalpatel2106.feature.project.list.adapter.ProjectListItem
import com.kevalpatel2106.feature.project.list.adapter.ProjectListItem.HeaderItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class InsertProjectListHeadersImplTest {

    @ParameterizedTest(name = "given before and after in the list when invoke then check {2} separator item added")
    @MethodSource("provideValues")
    fun `given before and after project in the list when invoke then check correct separator item added`(
        before: Project?,
        after: Project?,
        expected: ProjectListItem?,
    ) {
        val result = InsertProjectListHeadersImpl()(before, after)

        assertEquals(
            expected,
            result,
            "Inputs: Before ${before?.isFavourite} and After: ${after?.isFavourite}",
        )
    }

    companion object {
        private val kFixture = KFixture()

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: before, after, expected separator
            arguments(null, null, null),
            arguments(
                null,
                getProjectFixture(kFixture).copy(isFavourite = false),
                null,
            ),
            arguments(
                null,
                getProjectFixture(kFixture).copy(isFavourite = true),
                HeaderItem(R.string.list_header_favourites),
            ),
            arguments(
                getProjectFixture(kFixture).copy(isFavourite = true),
                getProjectFixture(kFixture).copy(isFavourite = true),
                null,
            ),
            arguments(
                getProjectFixture(kFixture).copy(isFavourite = true),
                getProjectFixture(kFixture).copy(isFavourite = false),
                HeaderItem(R.string.list_header_other),
            ),
            arguments(
                getProjectFixture(kFixture).copy(isFavourite = true),
                null,
                null,
            ),
            arguments(
                getProjectFixture(kFixture).copy(isFavourite = false),
                null,
                null,
            ),
        )
    }
}

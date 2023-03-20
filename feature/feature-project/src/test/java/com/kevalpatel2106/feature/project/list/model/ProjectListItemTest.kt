package com.kevalpatel2106.feature.project.list.model

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.feature.project.list.model.ProjectListItem.HeaderItem
import com.kevalpatel2106.feature.project.list.model.ProjectListItem.ProjectItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ProjectListItemTest {

    @ParameterizedTest(name = "given project list item {0} when item created them check key is {1}")
    @MethodSource("provideValuesForKeyTest")
    fun `given project list item when item created them check key`(
        inputProjectListItem: ProjectListItem,
        expectedKey: String,
    ) {
        assertEquals(expectedKey, inputProjectListItem.key)
    }

    companion object {
        private val kFixture = KFixture()
        private val projectItem = ProjectItem(getProjectFixture(kFixture))
        private val headerItem = HeaderItem(R.string.list_header_pinned)

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForKeyTest() = listOf(
            // Format: project list item, expected compare id
            arguments(projectItem, projectItem.project.toString()),
            arguments(headerItem, headerItem.title.toString()),
        )
    }
}

package com.kevalpatel2106.feature.project.list.adapter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.feature.project.R
import com.kevalpatel2106.feature.project.list.adapter.ProjectListItem.HeaderItem
import com.kevalpatel2106.feature.project.list.adapter.ProjectListItem.ProjectItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class ProjectListItemTest {

    @ParameterizedTest(name = "given project list item {0} when item created them check compare id is {1}")
    @MethodSource("provideValuesForCompareIdTest")
    fun `given project list item when item created them check compare id`(
        inputProjectListItem: ProjectListItem,
        expectedCompareId: String,
    ) {
        assertEquals(expectedCompareId, inputProjectListItem.compareId)
    }

    @ParameterizedTest(name = "given project list item {0} when item created them check list item type is {1}")
    @MethodSource("provideValuesForListItemTypeTest")
    fun `given project list item when item created them check list item type`(
        inputProjectListItem: ProjectListItem,
        expectedListItemType: ProjectListItemType,
    ) {
        assertEquals(expectedListItemType, inputProjectListItem.listItemType)
    }

    companion object {
        private val kFixture = KFixture()
        private val projectItem = ProjectItem(getProjectFixture(kFixture))
        private val headerItem = HeaderItem(R.string.list_header_favourites)

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForCompareIdTest() = listOf(
            // Format: project list item, expected compare id
            arguments(projectItem, projectItem.project.remoteId.toString()),
            arguments(headerItem, headerItem.title.toString()),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForListItemTypeTest() = listOf(
            // Format: project list item, expected list item type
            arguments(projectItem, ProjectListItemType.PROJECT_ITEM),
            arguments(headerItem, ProjectListItemType.HEADER_ITEM),
        )
    }
}

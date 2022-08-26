package com.kevalpatel2106.feature.project.list.adapter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.feature.project.R
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ProjectListDiffCallbackTest {

    @ParameterizedTest(name = "given old and new item when diff util called then check are content same {2}")
    @MethodSource("provideValuesForContentTest")
    fun `given old and new item when diff util called then check are content same or not`(
        oldItem: ProjectListItem,
        newItem: ProjectListItem,
        areContentSame: Boolean,
    ) {
        val result = ProjectListDiffCallback.areContentsTheSame(oldItem, newItem)

        assertEquals(areContentSame, result) { "Inputs: Old Item $oldItem and New item: $newItem" }
    }

    @ParameterizedTest(name = "given old and new item when diff util called then check are item same {2}")
    @MethodSource("provideValuesForItemTest")
    fun `given old and new item when diff util called then check are item same or not`(
        oldItem: ProjectListItem,
        newItem: ProjectListItem,
        areItemSame: Boolean,
    ) {
        val result = ProjectListDiffCallback.areItemsTheSame(oldItem, newItem)

        assertEquals(areItemSame, result) { "Inputs: Old Item $oldItem and New item: $newItem" }
    }

    companion object {
        private val kFixture = KFixture()
        private val project1 = getProjectFixture(kFixture).copy(remoteId = ProjectId("test"))
        private val project2 = getProjectFixture(kFixture).copy(remoteId = ProjectId("test1"))

        private val projectItem1 = ProjectListItem.ProjectItem(project1)
        private val projectItem2 = ProjectListItem.ProjectItem(project2)

        private val headerItem1 = ProjectListItem.HeaderItem(R.string.list_header_favourites)
        private val headerItem2 = ProjectListItem.HeaderItem(R.string.list_header_other)

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForContentTest() = listOf(
            // Format: old item, new item, are content same
            arguments(projectItem1, projectItem2, false),
            arguments(projectItem1, projectItem1.copy(), true),
            arguments(projectItem1, projectItem1.copy(project1.copy(name = "other one")), false),
            arguments(projectItem1, headerItem1, false),
            arguments(headerItem1, headerItem2, false),
            arguments(headerItem1, headerItem1.copy(), true),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForItemTest() = listOf(
            // Format: old item, new item, are items same
            arguments(projectItem1, projectItem2, false),
            arguments(projectItem1, projectItem1.copy(), true),
            arguments(projectItem1, projectItem1.copy(project1.copy(name = "other one")), true),
            arguments(projectItem1, headerItem1, false),
            arguments(headerItem1, headerItem2, false),
            arguments(headerItem1, headerItem1.copy(), true),
        )
    }
}

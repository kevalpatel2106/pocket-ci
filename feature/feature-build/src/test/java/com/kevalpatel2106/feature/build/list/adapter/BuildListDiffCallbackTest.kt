package com.kevalpatel2106.feature.build.list.adapter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getBuildFixture
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.feature.build.list.adapter.BuildListItem.BuildItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class BuildListDiffCallbackTest {

    @ParameterizedTest(name = "given old {0} and new {1} item when checking are contents same then return {2}")
    @MethodSource("provideAreContentSame")
    fun `given old and new item when checking are contents same then return expected value`(
        oldItem: BuildListItem,
        newItem: BuildListItem,
        expectedAreContentSame: Boolean,
    ) {
        val actual = BuildListDiffCallback.areContentsTheSame(oldItem, newItem)

        assertEquals(expectedAreContentSame, actual)
    }

    @ParameterizedTest(name = "given old {0} and new {1} item when checking are items same then return {2}")
    @MethodSource("provideAreItemSame")
    fun `given old and new item when checking are items same then return expected value`(
        oldItem: BuildListItem,
        newItem: BuildListItem,
        expectedAreItemSame: Boolean,
    ) {
        val actual = BuildListDiffCallback.areItemsTheSame(oldItem, newItem)

        assertEquals(expectedAreItemSame, actual)
    }

    companion object {
        private val fixture = KFixture()
        private val build = getBuildFixture(fixture)
        private val buildItem = fixture<BuildItem>().copy(build = build)

        @Suppress("unused")
        @JvmStatic
        fun provideAreContentSame() = listOf(
            // Format old item, new item, expected are content same
            arguments(buildItem, buildItem.copy(), true),
            arguments(
                buildItem.copy(commitHash = "jskxhccscbzxgcvzx7cvx"),
                buildItem.copy(commitHash = "msckjsdgfsdjfhjaksdasdlhfulsda"),
                false,
            ),
            arguments(
                buildItem.copy(build = build.copy(number = 1)),
                buildItem.copy(build = build.copy(number = 2)),
                false,
            ),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideAreItemSame() = listOf(
            // Format old item, new item, expected are item same
            arguments(buildItem, buildItem.copy(), true),
            arguments(
                buildItem.copy(commitHash = "jskxhccscbzxgcvzx7cvx"),
                buildItem.copy(commitHash = "msckjsdgfsdjfhjaksdasdlhfulsda"),
                true,
            ),
            arguments(
                buildItem.copy(build = build.copy(number = 1)),
                buildItem.copy(build = build.copy(number = 2)),
                true,
            ),
            arguments(
                buildItem.copy(build = build.copy(id = BuildId("1"))),
                buildItem.copy(build = build.copy(id = BuildId("2"))),
                false,
            ),
        )
    }
}

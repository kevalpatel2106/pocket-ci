package com.kevalpatel2106.feature.build.list.adapter

import com.flextrade.kfixture.KFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BuildListItemTest {
    private val fixture = KFixture()

    @Test
    fun `check list item type of build item`() {
        val actual = fixture<BuildListItem.BuildItem>().listItemType

        assertEquals(BuildListItemType.BUILD_ITEM, actual)
    }

    @Test
    fun `check compare id is same as build id of build item`() {
        val buildItem = fixture<BuildListItem.BuildItem>()

        val actual = buildItem.compareId

        assertEquals(buildItem.build.id.toString(), actual)
    }
}

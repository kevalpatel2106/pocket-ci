package com.kevalpatel2106.feature.build.list.model

import com.flextrade.kfixture.KFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BuildListItemTest {
    private val fixture = KFixture()

    @Test
    fun `check compare id is same as build id of build item`() {
        val buildItem = fixture<BuildListItem.BuildItem>()

        val actual = buildItem.key

        assertEquals(buildItem.buildId.toString(), actual)
    }
}

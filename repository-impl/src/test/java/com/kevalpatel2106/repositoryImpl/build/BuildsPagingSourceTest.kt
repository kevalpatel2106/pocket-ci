package com.kevalpatel2106.repositoryImpl.build

import androidx.paging.PagingSource
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.coreTest.getAccountBasicFixture
import com.kevalpatel2106.coreTest.getBuildFixture
import com.kevalpatel2106.coreTest.getProjectBasicFixture
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.repositoryImpl.build.BuildRepoImpl.Companion.PAGE_SIZE
import com.kevalpatel2106.repositoryImpl.getPagingStateFixture
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class BuildsPagingSourceTest {
    private val fixture = KFixture()
    private val ciConnector = mock<CIConnector>()
    private val loadParams = mock<PagingSource.LoadParams<String>> {
        on { key } doReturn fixture()
    }
    private val subject = BuildsPagingSource(
        getProjectBasicFixture(fixture),
        getAccountBasicFixture(fixture),
        ciConnector,
    )

    @Test
    fun `given empty builds from network when load then verify load result success with next key null`() =
        runTest {
            val nextCursor = null
            val builds = listOf<Build>()
            whenever(ciConnector.getBuildsByCreatedDesc(any(), any(), anyOrNull(), eq(PAGE_SIZE)))
                .thenReturn(PagedData(builds, nextCursor))

            val actual = subject.load(loadParams)

            val expected = PagingSource.LoadResult.Page(data = builds, prevKey = null, nextKey = null)
            assertEquals(expected, actual)
        }

    @Test
    fun `given builds from network with null cursor when load then verify load result success with next key null`() =
        runTest {
            val nextCursor = null
            val builds = listOf(getBuildFixture(fixture))
            whenever(ciConnector.getBuildsByCreatedDesc(any(), any(), anyOrNull(), eq(PAGE_SIZE)))
                .thenReturn(PagedData(builds, nextCursor))

            val actual = subject.load(loadParams)

            val expected = PagingSource.LoadResult.Page(data = builds, prevKey = null, nextKey = null)
            assertEquals(expected, actual)
        }

    @Test
    fun `given jobs from network with non-null cursor when load then verify load result success with next key`() =
        runTest {
            val nextCursor = fixture<String>()
            val builds = listOf(getBuildFixture(fixture))
            whenever(ciConnector.getBuildsByCreatedDesc(any(), any(), anyOrNull(), eq(PAGE_SIZE)))
                .thenReturn(PagedData(builds, nextCursor))

            val actual = subject.load(loadParams)

            val expected = PagingSource.LoadResult.Page(data = builds, prevKey = null, nextKey = nextCursor)
            assertEquals(expected, actual)
        }

    @Test
    fun `given build from network fails when load then load result is error`() = runTest {
        whenever(ciConnector.getBuildsByCreatedDesc(any(), any(), anyOrNull(), eq(PAGE_SIZE)))
            .thenThrow(IllegalStateException())

        val actual = subject.load(loadParams)

        assertTrue(actual is PagingSource.LoadResult.Error)
    }

    @Test
    fun `verify refresh key is null`() = runTest {
        val actual = subject.getRefreshKey(getPagingStateFixture(fixture))

        assertNull(actual)
    }
}

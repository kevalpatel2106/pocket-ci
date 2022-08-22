package com.kevalpatel2106.repositoryImpl.artifact

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.coreTest.getAccountBasicFixture
import com.kevalpatel2106.coreTest.getArtifactFixture
import com.kevalpatel2106.coreTest.getBuildIdFixture
import com.kevalpatel2106.coreTest.getProjectBasicFixture
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.repositoryImpl.getPagingStateFixture
import com.kevalpatel2106.repositoryImpl.job.JobRepoImpl.Companion.PAGE_SIZE
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

internal class ArtifactsPagingSourceTest {
    private val fixture = KFixture()
    private val ciConnector = mock<CIConnector>()
    private val loadParams = mock<PagingSource.LoadParams<String>> {
        on { key } doReturn fixture()
    }
    private val subject = ArtifactsPagingSource(
        getProjectBasicFixture(fixture),
        getAccountBasicFixture(fixture),
        getBuildIdFixture(fixture),
        ciConnector,
    )

    @Test
    fun `given empty artifacts from network when load then verify load result success with next key null`() =
        runTest {
            val nextCursor = null
            val artifacts = listOf<Artifact>()
            whenever(ciConnector.getArtifacts(any(), any(), any(), anyOrNull(), eq(PAGE_SIZE)))
                .thenReturn(PagedData(artifacts, nextCursor))

            val actual = subject.load(loadParams)

            val expected = LoadResult.Page(data = artifacts, prevKey = null, nextKey = null)
            assertEquals(expected, actual)
        }

    @Test
    fun `given artifacts from network with null cursor when load then verify load result success with next key null`() =
        runTest {
            val nextCursor = null
            val artifacts = listOf(getArtifactFixture(fixture))
            whenever(ciConnector.getArtifacts(any(), any(), any(), anyOrNull(), eq(PAGE_SIZE)))
                .thenReturn(PagedData(artifacts, nextCursor))

            val actual = subject.load(loadParams)

            val expected = LoadResult.Page(data = artifacts, prevKey = null, nextKey = null)
            assertEquals(expected, actual)
        }

    @Test
    fun `given artifacts from network with non-null cursor when load then verify load result success with next key`() =
        runTest {
            val nextCursor = fixture<String>()
            val artifacts = listOf(getArtifactFixture(fixture))
            whenever(ciConnector.getArtifacts(any(), any(), any(), anyOrNull(), eq(PAGE_SIZE)))
                .thenReturn(PagedData(artifacts, nextCursor))

            val actual = subject.load(loadParams)

            val expected = LoadResult.Page(data = artifacts, prevKey = null, nextKey = nextCursor)
            assertEquals(expected, actual)
        }

    @Test
    fun `given artifacts from network fails when load then load result is error`() = runTest {
        whenever(ciConnector.getArtifacts(any(), any(), any(), anyOrNull(), eq(PAGE_SIZE)))
            .thenThrow(IllegalStateException())

        val actual = subject.load(loadParams)

        assertTrue(actual is LoadResult.Error)
    }

    @Test
    fun `verify refresh key is null`() = runTest {
        val actual = subject.getRefreshKey(getPagingStateFixture(fixture))

        assertNull(actual)
    }
}

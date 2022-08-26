package com.kevalpatel2106.connector.github

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.dto.BuildDto
import com.kevalpatel2106.connector.github.network.dto.BuildListDto
import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.connector.github.network.mapper.BuildMapper
import com.kevalpatel2106.coreTest.getAccountBasicFixture
import com.kevalpatel2106.coreTest.getBuildFixture
import com.kevalpatel2106.coreTest.getProjectBasicFixture
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.exception.FeatureNotSupportedException
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class GitHubBuildInfoProviderTest {
    private val projectBasic = getProjectBasicFixture(fixture)
    private val accountBasic = getAccountBasicFixture(fixture)
    private val mappedBuild = getBuildFixture(fixture)

    private val githubEndpoint = mock<GitHubEndpoint>()
    private val retrofitClient = mock<GitHubRetrofitClient> {
        on { getGithubService(any(), any()) } doReturn githubEndpoint
    }
    private val buildMapper = mock<BuildMapper> {
        on { invoke(any(), any()) } doReturn mappedBuild
    }

    private val subject = GitHubBuildInfoProvider(retrofitClient, buildMapper)

    // region getBuildsByCreatedDesc
    @ParameterizedTest(name = "given cursor {0} when builds page api call made then verify api call parameters with page number {1}")
    @MethodSource("provideValuesForApiParameterTest")
    fun `given cursor when builds page api call made then verify api call parameters`(
        inputCursor: String?,
        expectedPageNumber: Int,
    ) = runTest {
        val limit = fixture<Int>()
        whenever(githubEndpoint.getBuildsTriggeredAtDesc(any(), any(), any(), any()))
            .thenReturn(fixture())

        subject.getBuildsByCreatedDesc(projectBasic, accountBasic, inputCursor, limit)

        verify(githubEndpoint).getBuildsTriggeredAtDesc(
            projectBasic.owner,
            projectBasic.name,
            limit,
            expectedPageNumber,
        )
    }

    @ParameterizedTest(name = "given current cursor {0} and response build list when builds page api call made then verify next page cursor {2}")
    @MethodSource("provideValuesForNextCursorTest")
    fun `given current cursor and response build list when builds page api call made then verify next page cursor`(
        currentCursor: String?,
        responseBuilds: List<BuildDto>,
        expectedNextCursor: String?,
    ) = runTest {
        whenever(githubEndpoint.getBuildsTriggeredAtDesc(any(), any(), any(), any()))
            .thenReturn(BuildListDto(fixture(), responseBuilds))

        val data =
            subject.getBuildsByCreatedDesc(projectBasic, accountBasic, currentCursor, fixture())

        assertEquals(expectedNextCursor, data.nextCursor)
    }

    @Test
    fun `given non-empty response build list when builds page api call made then verify paged data`() =
        runTest {
            val buildDto = listOf<BuildDto>(fixture(), fixture())
            whenever(githubEndpoint.getBuildsTriggeredAtDesc(any(), any(), any(), any()))
                .thenReturn(BuildListDto(fixture(), buildDto))

            val data =
                subject.getBuildsByCreatedDesc(projectBasic, accountBasic, fixture(), fixture())

            assertEquals(listOf(mappedBuild, mappedBuild), data.data)
        }

    @Test
    fun `given empty response build list when builds page api call made then verify paged data`() =
        runTest {
            whenever(githubEndpoint.getBuildsTriggeredAtDesc(any(), any(), any(), any()))
                .thenReturn(BuildListDto(fixture(), listOf()))

            val data =
                subject.getBuildsByCreatedDesc(projectBasic, accountBasic, fixture(), fixture())

            assertEquals(PagedData(listOf<Build>(), null), data)
        }

    @Test
    fun `given error getting builds when builds page loads then exception thrown`() = runTest {
        whenever(githubEndpoint.getBuildsTriggeredAtDesc(any(), any(), any(), any()))
            .thenThrow(IllegalStateException())

        assertThrows<IllegalStateException> {
            subject.getBuildsByCreatedDesc(projectBasic, accountBasic, fixture(), fixture())
        }
    }
    // endregion

    // region getBuildLogs
    @Test
    fun `when get build logs called then feature not supported exception thrown`() = runTest {
        val exception = assertThrows<FeatureNotSupportedException> {
            subject.getBuildLogs(projectBasic, accountBasic, fixture())
        }
        assertEquals(CIType.GITHUB, exception.ciType)
    }
    // endregion

    companion object {
        private val fixture = KFixture()

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForApiParameterTest() = listOf(
            // Format current cursor, cursor in api parameters
            arguments("1", 1),
            arguments("invalid-cursor", 1),
            arguments(null, 1),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForNextCursorTest() = listOf(
            // Format current page, response build list, next cursor
            arguments(null, listOf<BuildDto>(), null),
            arguments(null, listOf<BuildDto>(fixture(), fixture()), "2"),

            arguments("invalid-cursor", listOf<BuildDto>(), null),
            arguments("invalid-cursor", listOf<BuildDto>(fixture(), fixture()), "2"),

            arguments("100", listOf<BuildDto>(), null),
            arguments("100", listOf<BuildDto>(fixture(), fixture()), "101"),
        )
    }
}

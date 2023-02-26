package com.kevalpatel2106.connector.github

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.dto.ProjectDto
import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.connector.github.network.mapper.ProjectMapper
import com.kevalpatel2106.coreTest.getAccountBasicFixture
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.entity.PagedData
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

internal class GitHubProjectInfoProviderTest {
    private val mappedProject = getProjectFixture(fixture)
    private val accountBasic = getAccountBasicFixture(fixture)

    private val githubEndpoint = mock<GitHubEndpoint>()
    private val retrofitClient = mock<GitHubRetrofitClient> {
        on { getGithubService(any(), any()) } doReturn githubEndpoint
    }
    private val projectMapper = mock<ProjectMapper> {
        on { invoke(any(), any()) } doReturn mappedProject
    }

    private val subject = GitHubProjectInfoProvider(retrofitClient, projectMapper)

    // region getProjectsUpdatedDesc
    @ParameterizedTest(
        name = "given cursor {0} when projects page api call made then verify api call parameters with page number {1}"
    )
    @MethodSource("provideValuesForApiParameterTest")
    fun `given cursor when projects page api call made then verify api call parameters`(
        inputCursor: String?,
        expectedPageNumber: Int,
    ) = runTest {
        val limit = fixture<Int>()
        whenever(githubEndpoint.getProjectsUpdatedDesc(any(), any(), any(), any(), any()))
            .thenReturn(fixture())

        subject.getProjectsUpdatedDesc(accountBasic, inputCursor, limit)

        verify(githubEndpoint).getProjectsUpdatedDesc(
            page = expectedPageNumber,
            visibility = "all",
            sort = "updated",
            direction = "desc",
            perPage = limit,
        )
    }

    @ParameterizedTest(
        name = "given current cursor {0} and response project list when projects page api call made then verify next page cursor {2}"
    )
    @MethodSource("provideValuesForNextCursorTest")
    fun `given current cursor and response project list when projects page api call made then verify next page cursor`(
        currentCursor: String?,
        responseProjects: List<ProjectDto>,
        expectedNextCursor: String?,
    ) = runTest {
        whenever(githubEndpoint.getProjectsUpdatedDesc(any(), any(), any(), any(), any()))
            .thenReturn(responseProjects)

        val data = subject.getProjectsUpdatedDesc(accountBasic, currentCursor, fixture())

        assertEquals(expectedNextCursor, data.nextCursor)
    }

    @Test
    fun `given non-empty response project list when projects page api call made then verify paged data`() =
        runTest {
            val projectDtos = listOf<ProjectDto>(fixture(), fixture())
            whenever(githubEndpoint.getProjectsUpdatedDesc(any(), any(), any(), any(), any()))
                .thenReturn(projectDtos)

            val data = subject.getProjectsUpdatedDesc(accountBasic, fixture(), fixture())

            assertEquals(listOf(mappedProject, mappedProject), data.data)
        }

    @Test
    fun `given empty response project list when projects page api call made then verify paged data`() =
        runTest {
            whenever(githubEndpoint.getProjectsUpdatedDesc(any(), any(), any(), any(), any()))
                .thenReturn(listOf())

            val data = subject.getProjectsUpdatedDesc(accountBasic, fixture(), fixture())

            assertEquals(PagedData(listOf<ProjectDto>(), null), data)
        }

    @Test
    fun `given error getting projects when projects page loads then exception thrown`() = runTest {
        whenever(githubEndpoint.getProjectsUpdatedDesc(any(), any(), any(), any(), any()))
            .thenThrow(IllegalStateException())

        assertThrows<IllegalStateException> {
            subject.getProjectsUpdatedDesc(accountBasic, fixture(), fixture())
        }
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
            // Format current page, response project list, next cursor
            arguments(null, listOf<ProjectDto>(), null),
            arguments(null, listOf<ProjectDto>(fixture(), fixture()), "2"),

            arguments("invalid-cursor", listOf<ProjectDto>(), null),
            arguments("invalid-cursor", listOf<ProjectDto>(fixture(), fixture()), "2"),

            arguments("100", listOf<ProjectDto>(), null),
            arguments("100", listOf<ProjectDto>(fixture(), fixture()), "101"),
        )
    }
}

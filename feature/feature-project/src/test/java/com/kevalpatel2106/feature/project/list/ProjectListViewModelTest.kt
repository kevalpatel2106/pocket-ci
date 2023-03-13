package com.kevalpatel2106.feature.project.list

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.feature.project.list.model.ProjectListVMEvent
import com.kevalpatel2106.feature.project.list.model.ProjectListVMEvent.OpenBuildsList
import com.kevalpatel2106.feature.project.list.usecase.InsertProjectListHeaders
import com.kevalpatel2106.repository.ProjectRepo
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@ExtendWith(TestCoroutineExtension::class)
internal class ProjectListViewModelTest {
    private val kFixture = KFixture()
    private val projectRepo = mock<ProjectRepo> {
        on { getProjects(any()) } doReturn emptyFlow()
    }
    private val displayErrorMapper = mock<DisplayErrorMapper>()
    private val navArgs = kFixture<ProjectListFragmentArgs>()
    private val insertProjectListHeaders = mock<InsertProjectListHeaders> {
        on { invoke(anyOrNull(), anyOrNull()) } doReturn null
    }

    private val subject by lazy {
        ProjectListViewModel(
            navArgs.toSavedStateHandle(),
            projectRepo,
            insertProjectListHeaders,
            displayErrorMapper,
        )
    }

    @Test
    fun `given selected project when on project selected then check builds page opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val selectedProject = getProjectFixture(kFixture)

            subject.onProjectSelected(selectedProject)

            assertEquals(
                OpenBuildsList(selectedProject.accountId, selectedProject.remoteId),
                flowTurbine.awaitItem(),
            )
        }

    @Test
    fun `when close called then check projects list closed`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.close()

            assertEquals(ProjectListVMEvent.Close, flowTurbine.awaitItem())
        }

    @Test
    fun `given project is already pined when toggle pin called to pin project then verify nothing happens`() =
        runTest {
            val project = getProjectFixture(kFixture).copy(isPinned = true)
            val isChecked = true

            subject.togglePin(project, isChecked)
            advanceUntilIdle()

            verify(projectRepo, never()).unpinProject(project.remoteId, project.accountId)
        }

    @Test
    fun `given project is pined when toggle pin called to unpin project then verify project unpinned`() =
        runTest {
            val project = getProjectFixture(kFixture).copy(isPinned = true)
            val isChecked = false

            subject.togglePin(project, isChecked)
            advanceUntilIdle()

            verify(projectRepo).unpinProject(project.remoteId, project.accountId)
        }

    @Test
    fun `given project is not pined when toggle pin called to unpin project then verify nothing happens`() =
        runTest {
            val project = getProjectFixture(kFixture).copy(isPinned = false)
            val isChecked = false

            subject.togglePin(project, isChecked)
            advanceUntilIdle()

            verify(projectRepo, never()).pinProject(project.remoteId, project.accountId)
        }

    @Test
    fun `given project is not pined when toggle pin called to pin project then verify nothing happens`() =
        runTest {
            val project = getProjectFixture(kFixture).copy(isPinned = false)
            val isChecked = true

            subject.togglePin(project, isChecked)
            advanceUntilIdle()

            verify(projectRepo).pinProject(project.remoteId, project.accountId)
        }
}

package com.kevalpatel2106.feature.project.list

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.feature.project.list.ProjectListVMEvent.OpenBuildsList
import com.kevalpatel2106.feature.project.list.usecase.InsertProjectListHeaders
import com.kevalpatel2106.repository.ProjectRepo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExtendWith(TestCoroutineExtension::class)
class ProjectListViewModelTest {
    private val kFixture = KFixture()
    private val projectRepo = mock<ProjectRepo>()
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
    fun `when reload called then check projects list refreshed`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.reload()

            assertEquals(ProjectListVMEvent.RefreshProjects, flowTurbine.awaitItem())
        }

    @Test
    fun `when retry next page called then check retry loading event emitted`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.retryNextPage()

            assertEquals(ProjectListVMEvent.RetryLoading, flowTurbine.awaitItem())
        }
}

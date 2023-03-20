package com.kevalpatel2106.registration.ciSelection

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.latestValue
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.analytics.ClickEvent
import com.kevalpatel2106.registration.ciSelection.model.CISelectionVMEvent
import com.kevalpatel2106.registration.ciSelection.model.CISelectionVMEvent.Close
import com.kevalpatel2106.registration.ciSelection.model.CISelectionViewState
import com.kevalpatel2106.repository.AnalyticsRepo
import com.kevalpatel2106.repository.CIInfoRepo
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(TestCoroutineExtension::class)
internal class CISelectionViewModelTest {
    private val kFixture = KFixture()
    private val selectionRepo = mock<CIInfoRepo>()
    private val analyticsRepo = mock<AnalyticsRepo>()
    private val supportedCIs = listOf<CIInfo>(kFixture(), kFixture())

    private val subject by lazy { CISelectionViewModel(selectionRepo, analyticsRepo) }

    @Test
    fun `when view model loads check the initial state`() = runTest {
        assertEquals(
            CISelectionViewState.initialState(),
            subject.viewState.latestValue(this),
        )
    }

    @Test
    fun `given list of supported CIs when view model loads check the success state`() = runTest {
        whenever(selectionRepo.getSupportedCI()).thenReturn(supportedCIs)

        assertEquals(
            CISelectionViewState.SuccessState(supportedCIs),
            subject.viewState.latestValue(this),
        )
    }

    @Test
    fun `given lading of supported CIs fails when view model loads check the error state`() =
        runTest {
            val error = IllegalStateException("Error")
            whenever(selectionRepo.getSupportedCI()).thenThrow(error)

            assertEquals(
                CISelectionViewState.ErrorState(error),
                subject.viewState.latestValue(this),
            )
        }

    @Test
    fun `given view model initialised when reload called check supported CI info loads again`() =
        runTest {
            whenever(selectionRepo.getSupportedCI()).thenReturn(supportedCIs)

            subject.reload()
            advanceUntilIdle()

            verify(selectionRepo, times(2)).getSupportedCI()
        }

    @Test
    fun `given supported CI loaded when CI selected check register account screen opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            whenever(selectionRepo.getSupportedCI()).thenReturn(supportedCIs)

            subject.onCISelected(supportedCIs.first())

            assertEquals(
                CISelectionVMEvent.OpenRegisterAccount(supportedCIs.first()),
                flowTurbine.awaitItem(),
            )
        }

    @Test
    fun `given supported CI loaded when CI selected the click event logged`() = runTest {
        whenever(selectionRepo.getSupportedCI()).thenReturn(supportedCIs)

        subject.onCISelected(supportedCIs.first())

        verify(analyticsRepo).sendEvent(
            ClickEvent(
                action = ClickEvent.Action.CI_SELECTED,
                label = supportedCIs.first().type.name,
            ),
        )
    }

    @Test
    fun `given view model initialised when close then verify close event emit`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { testScope, flowTurbine ->
            subject.close()
            testScope.advanceUntilIdle()

            assertEquals(Close, flowTurbine.awaitItem())
        }
}

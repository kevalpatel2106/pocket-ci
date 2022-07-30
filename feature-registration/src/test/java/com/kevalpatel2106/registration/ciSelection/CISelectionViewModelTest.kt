package com.kevalpatel2106.registration.ciSelection

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.latestValue
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.entity.CIInfo
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
class CISelectionViewModelTest {
    private val kFixture = KFixture()
    private val selectionRepo = mock<CIInfoRepo>()
    private val supportedCIs = listOf<CIInfo>(kFixture(), kFixture())

    private val subject by lazy { CISelectionViewModel(selectionRepo) }

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
}

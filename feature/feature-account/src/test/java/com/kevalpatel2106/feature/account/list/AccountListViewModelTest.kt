package com.kevalpatel2106.feature.account.list

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.entity.analytics.ClickEvent
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.AccountRemovedSuccess
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.Close
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.InvalidateOptionsMenu
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.OpenCiSelection
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.OpenProjects
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.ShowDeleteConfirmation
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.ShowErrorRemovingAccount
import com.kevalpatel2106.feature.account.list.model.AccountListVMEvent.ShowErrorSelectingAccount
import com.kevalpatel2106.feature.account.list.usecase.AccountItemMapper
import com.kevalpatel2106.feature.account.list.usecase.InsertAccountListHeaders
import com.kevalpatel2106.repository.AccountRepo
import com.kevalpatel2106.repository.AnalyticsRepo
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(TestCoroutineExtension::class)
internal class AccountListViewModelTest {
    private val fixture = KFixture()
    private val displayError = fixture<DisplayError>()
    private val displayErrorMapper = mock<DisplayErrorMapper> {
        on { invoke(any(), any(), any()) } doReturn displayError
    }
    private val accountRepo = mock<AccountRepo>()
    private val analyticsRepo = mock<AnalyticsRepo>()
    private val insertAccountListHeaders = mock<InsertAccountListHeaders> {
        on { invoke(anyOrNull(), anyOrNull()) } doReturn null
    }
    private val accountItemMapper = mock<AccountItemMapper>()

    private val subject = AccountListViewModel(
        accountRepo,
        insertAccountListHeaders,
        accountItemMapper,
        displayErrorMapper,
        analyticsRepo,
    )

    @Test
    fun `when initialised then check edit mode is off`() {
        val actual = subject.viewState.value.isEditModeOn

        assertFalse(actual)
    }

    @Test
    fun `when account selected successfully then projects screen opened`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val accountToSelect = getAccountFixture(fixture)
            whenever(accountRepo.setSelectedAccount(accountToSelect.localId)).thenReturn(Unit)

            subject.onAccountSelected(accountToSelect.localId)

            assertEquals(OpenProjects(accountToSelect.localId), flowTurbine.awaitItem())
        }

    @Test
    fun `when account selection fails then error message displayed`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val accountToSelect = getAccountFixture(fixture)
            whenever(accountRepo.setSelectedAccount(accountToSelect.localId))
                .thenThrow(IllegalStateException("Error"))

            subject.onAccountSelected(accountToSelect.localId)

            assertEquals(ShowErrorSelectingAccount(displayError), flowTurbine.awaitItem())
        }

    @Test
    fun `when account removed then check delete confirmation dialog appears`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val accountToRemove = getAccountFixture(fixture)

            subject.onAccountRemoved(accountToRemove)

            assertEquals(
                ShowDeleteConfirmation(accountToRemove.localId, accountToRemove.name),
                flowTurbine.awaitItem(),
            )
        }

    @Test
    fun `given account deletes successfully when account delete confirmed then success message displayed`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val accountToRemove = getAccountFixture(fixture)
            whenever(accountRepo.removeAccount(accountToRemove.localId)).thenReturn(Unit)

            subject.onAccountDeleteConfirmed(accountToRemove.localId)

            assertEquals(AccountRemovedSuccess, flowTurbine.awaitItem())
        }

    @Test
    fun `given account deletes fails when account delete confirmed then error message displayed`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val accountToRemove = getAccountFixture(fixture)
            whenever(accountRepo.removeAccount(accountToRemove.localId))
                .thenThrow(IllegalStateException("Error!"))

            subject.onAccountDeleteConfirmed(accountToRemove.localId)

            assertEquals(ShowErrorRemovingAccount(displayError), flowTurbine.awaitItem())
        }

    @Test
    fun `when add account clicked then CI selection screen opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onAddAccountClicked()

            assertEquals(OpenCiSelection, flowTurbine.awaitItem())
        }

    @Test
    fun `when add account clicked then click event logged`() = runTest {
        subject.onAddAccountClicked()

        verify(analyticsRepo).sendEvent(ClickEvent(ClickEvent.Action.ACCOUNTS_ADD_ACCOUNT_CLICKED))
    }

    @Test
    fun `when edit mode turned on then edit mode turned on in view state`() = runTest {
        subject.editModeStatus(true)
        advanceUntilIdle()

        val actual = subject.viewState.value
        assertTrue(actual.isEditModeOn)
    }

    @Test
    fun `when edit mode turned on then options menu invalidate`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.editModeStatus(true)

            val actual = flowTurbine.awaitItem()

            assertEquals(InvalidateOptionsMenu, actual)
        }

    @Test
    fun `given edit mode turned on when calling isInEditMode on then it returns true`() {
        subject.editModeStatus(true)

        val actual = subject.isInEditMode()

        assertTrue(actual)
    }

    @Test
    fun `given view model initialised when close then verify close event emit`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.close()

            assertEquals(Close, flowTurbine.awaitItem())
        }
}

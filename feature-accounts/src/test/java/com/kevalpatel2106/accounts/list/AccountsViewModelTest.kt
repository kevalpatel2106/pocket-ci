package com.kevalpatel2106.accounts.list

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.accounts.list.AccountsVMEvent.ShowDeleteConfirmation
import com.kevalpatel2106.accounts.list.usecase.ConvertToAccountItem
import com.kevalpatel2106.accounts.list.usecase.InsertAccountListHeaders
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.repository.AccountRepo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(TestCoroutineExtension::class)
class AccountsViewModelTest {
    private val kFixture = KFixture()
    private val accountRepo = mock<AccountRepo>()
    private val insertAccountListHeaders = mock<InsertAccountListHeaders> {
        on { invoke(anyOrNull(), anyOrNull()) } doReturn null
    }
    private val convertToAccountItem = mock<ConvertToAccountItem>()

    private val subject by lazy {
        AccountsViewModel(
            accountRepo,
            insertAccountListHeaders,
            convertToAccountItem,
        )
    }

    @Test
    fun `when account selected successfully then projects screen opened`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val accountToSelect = getAccountFixture(kFixture)
            whenever(accountRepo.setSelectedAccount(accountToSelect.localId)).thenReturn(Unit)

            subject.onAccountSelected(accountToSelect.localId)

            assertEquals(
                AccountsVMEvent.OpenProjects(accountToSelect.localId),
                flowTurbine.awaitItem(),
            )
        }

    @Test
    fun `when account selection fails then error message displayed`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val accountToSelect = getAccountFixture(kFixture)
            whenever(accountRepo.setSelectedAccount(accountToSelect.localId))
                .thenThrow(IllegalStateException("Error"))

            subject.onAccountSelected(accountToSelect.localId)

            assertEquals(AccountsVMEvent.ShowErrorSelectingAccount, flowTurbine.awaitItem())
        }

    @Test
    fun `when account removed then check delete confirmation dialog appears`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val accountToRemove = getAccountFixture(kFixture)

            subject.onAccountRemoved(accountToRemove)

            assertEquals(
                ShowDeleteConfirmation(accountToRemove.localId, accountToRemove.name),
                flowTurbine.awaitItem(),
            )
        }

    @Test
    fun `given account deletes successfully when account delete confirmed then success message displayed`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val accountToRemove = getAccountFixture(kFixture)
            whenever(accountRepo.removeAccount(accountToRemove.localId)).thenReturn(Unit)

            subject.onAccountDeleteConfirmed(accountToRemove.localId)

            assertEquals(AccountsVMEvent.AccountRemovedSuccess, flowTurbine.awaitItem())
        }

    @Test
    fun `given account deletes fails when account delete confirmed then error message displayed`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val accountToRemove = getAccountFixture(kFixture)
            whenever(accountRepo.removeAccount(accountToRemove.localId))
                .thenThrow(IllegalStateException("Error!"))

            subject.onAccountDeleteConfirmed(accountToRemove.localId)

            assertEquals(AccountsVMEvent.ShowErrorRemovingAccount, flowTurbine.awaitItem())
        }

    @Test
    fun `when reload called then refresh account event emitted`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.reload()

            assertEquals(AccountsVMEvent.RefreshAccounts, flowTurbine.awaitItem())
        }

    @Test
    fun `when add account clicked then CI selection screen opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onAddAccountClicked()

            assertEquals(AccountsVMEvent.OpenCiSelection, flowTurbine.awaitItem())
        }

    @Test
    fun `when retry loading the next page then retry loading event emitted`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.retryNextPage()

            assertEquals(AccountsVMEvent.RetryLoading, flowTurbine.awaitItem())
        }
}

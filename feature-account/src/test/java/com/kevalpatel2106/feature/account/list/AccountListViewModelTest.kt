package com.kevalpatel2106.feature.account.list

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.AccountRemovedSuccess
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.Close
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.OpenCiSelection
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.OpenProjects
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.RefreshAccountList
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.RetryLoading
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowDeleteConfirmation
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowErrorRemovingAccount
import com.kevalpatel2106.feature.account.list.AccountListVMEvent.ShowErrorSelectingAccount
import com.kevalpatel2106.feature.account.list.usecase.AccountItemMapper
import com.kevalpatel2106.feature.account.list.usecase.InsertAccountListHeaders
import com.kevalpatel2106.repository.AccountRepo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(TestCoroutineExtension::class)
class AccountListViewModelTest {
    private val fixture = KFixture()
    private val displayError = fixture<DisplayError>()
    private val displayErrorMapper = mock<DisplayErrorMapper> {
        on { invoke(any(), any(), any()) } doReturn displayError
    }
    private val accountRepo = mock<AccountRepo>()
    private val insertAccountListHeaders = mock<InsertAccountListHeaders> {
        on { invoke(anyOrNull(), anyOrNull()) } doReturn null
    }
    private val accountItemMapper = mock<AccountItemMapper>()

    private val subject by lazy {
        AccountListViewModel(
            accountRepo,
            insertAccountListHeaders,
            accountItemMapper,
            displayErrorMapper,
        )
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
    fun `when reload called then refresh account event emitted`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.reload()

            assertEquals(RefreshAccountList, flowTurbine.awaitItem())
        }

    @Test
    fun `when add account clicked then CI selection screen opens`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onAddAccountClicked()

            assertEquals(OpenCiSelection, flowTurbine.awaitItem())
        }

    @Test
    fun `when retry loading the next page then retry loading event emitted`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.retryNextPage()

            assertEquals(RetryLoading, flowTurbine.awaitItem())
        }

    @Test
    fun `given view model initialised when close then verify close event emit`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.close()

            assertEquals(Close, flowTurbine.awaitItem())
        }
}

package com.kevalpatel2106.pocketci.splash

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.NightMode
import com.kevalpatel2106.repository.AccountRepo
import com.kevalpatel2106.repository.SettingsRepo
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExtendWith(TestCoroutineExtension::class)
class SplashViewModelTest {
    private val kFixture = KFixture()
    private val accountRepo = mock<AccountRepo>()
    private val settingsRepo = mock<SettingsRepo>()

    private val subject by lazy { SplashViewModel(accountRepo, settingsRepo) }

    @Test
    fun `given night mode set to auto when nigh mode changed to on then view state changed`() =
        runTest {
            whenever(settingsRepo.getNightMode()).thenReturn(NightMode.ON)
            setUpAccountInfo()

            val subject = SplashViewModel(accountRepo, settingsRepo)
            advanceUntilIdle()

            assertEquals(NightMode.ON, subject.viewState.value.nightMode)
        }

    @Test
    fun `given night mode set to on when error occurred nigh mode changed to on then night mode set to auto`() =
        runTest {
            setUpAccountInfo()
            whenever(settingsRepo.getNightMode()).thenThrow(IllegalStateException("Error"))

            val subject = SplashViewModel(accountRepo, settingsRepo)
            advanceUntilIdle()

            assertEquals(NightMode.AUTO, subject.viewState.value.nightMode)
        }

    @Test
    fun `given night mode set to on when error occurred nigh mode changed to on then error message displayed`() {
        runTest {
            setUpAccountInfo()
            whenever(settingsRepo.getNightMode()).thenThrow(IllegalStateException("Error"))
        }
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            assertEquals(SplashVMEvent.ErrorLoadingTheme, flowTurbine.awaitItem())
        }
    }

    @Test
    fun `given no account selected when splash screen opens check user redirects to register screen`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            whenever(accountRepo.hasAnyAccount()).thenReturn(false)
            whenever(accountRepo.getSelectedAccount()).thenReturn(getAccountFixture(kFixture))
            whenever(settingsRepo.getNightMode()).thenReturn(NightMode.ON)

            assertEquals(SplashVMEvent.OpenRegisterAccount, flowTurbine.awaitItem())
        }

    @Test
    fun `given account selected when splash screen opens check user redirects to project screen`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val account = setUpAccountInfo()
            whenever(settingsRepo.getNightMode()).thenReturn(NightMode.ON)

            assertEquals(SplashVMEvent.OpenProjects(account.localId), flowTurbine.awaitItem())
        }

    @Test
    fun `given get selected account fail when splash screen opens check application closes`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            whenever(settingsRepo.getNightMode()).thenReturn(NightMode.ON)
            whenever(accountRepo.hasAnyAccount()).thenReturn(true)
            whenever(accountRepo.getSelectedAccount()).thenThrow(IllegalAccessError("Error!"))

            assertEquals(SplashVMEvent.CloseApplication, flowTurbine.awaitItem())
        }

    @Test
    fun `given has any account check account fail when splash screen opens check application closes`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            whenever(settingsRepo.getNightMode()).thenReturn(NightMode.ON)
            whenever(accountRepo.hasAnyAccount()).thenThrow(IllegalAccessError("Error!"))
            whenever(accountRepo.getSelectedAccount()).thenReturn(getAccountFixture(kFixture))

            assertEquals(SplashVMEvent.CloseApplication, flowTurbine.awaitItem())
        }

    private suspend fun setUpAccountInfo(): Account {
        val account = getAccountFixture(kFixture)
        whenever(accountRepo.hasAnyAccount()).thenReturn(true)
        whenever(accountRepo.getSelectedAccount()).thenReturn(account)
        return account
    }
}

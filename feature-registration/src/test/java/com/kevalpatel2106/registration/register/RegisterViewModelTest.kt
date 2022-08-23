package com.kevalpatel2106.registration.register

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.coreTest.getCIInfoFixture
import com.kevalpatel2106.coreTest.latestValue
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.entity.toToken
import com.kevalpatel2106.entity.toUrl
import com.kevalpatel2106.registration.R
import com.kevalpatel2106.registration.register.RegisterVMEvent.AccountAlreadyAdded
import com.kevalpatel2106.registration.register.RegisterVMEvent.HandleAuthSuccess
import com.kevalpatel2106.registration.register.RegisterVMEvent.ShowErrorAddingAccount
import com.kevalpatel2106.registration.register.usecase.SanitiseRegisterInput
import com.kevalpatel2106.registration.register.usecase.ValidateRegisterInput
import com.kevalpatel2106.repository.AccountRepo
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(TestCoroutineExtension::class)
class RegisterViewModelTest {
    private val kFixture = KFixture()
    private val sanitisedUrl = "http://example1.com/"
    private val sanitisedToken = "token"

    private val navArgs = RegisterFragmentArgs(selectedCI = getCIInfoFixture(kFixture()))
    private val accountRepo = mock<AccountRepo>()
    private val displayError = kFixture<DisplayError>()
    private val displayErrorMapper = mock<DisplayErrorMapper> {
        on { invoke(any(), any(), any()) } doReturn displayError
    }

    private val sanitiseRegisterInput = mock<SanitiseRegisterInput>()
    private val validateRegisterInput = mock<ValidateRegisterInput>()
    private val subject by lazy {
        RegisterViewModel(
            navArgs.toSavedStateHandle(),
            accountRepo,
            sanitiseRegisterInput,
            validateRegisterInput,
            displayErrorMapper,
        )
    }

    @BeforeEach
    fun before() {
        whenever(sanitiseRegisterInput(any(), any()))
            .thenReturn(sanitisedUrl to sanitisedToken)
    }

    @Test
    fun `given navargs when view model initialised then check initial state`() = runTest {
        val initialState = subject.viewState.latestValue(this)

        val expected = RegisterViewState.initialState(navArgs.selectedCI)
        assertEquals(expected, initialState)
    }

    @Test
    fun `given invalid url when submit check url error message displayed`() = runTest {
        whenever(validateRegisterInput(any(), any())).thenReturn(false to true)

        subject.submit("ebc.com", "token")

        assertEquals(
            R.string.error_invalid_domain_name,
            subject.viewState.latestValue(this).urlErrorMsg,
        )
    }

    @Test
    fun `given invalid token when submit check token error message displayed`() = runTest {
        whenever(validateRegisterInput(any(), any())).thenReturn(true to false)

        subject.submit("http://example.com", "token")

        assertEquals(
            R.string.error_invalid_token_name,
            subject.viewState.latestValue(this).tokenErrorMsg,
        )
    }

    @Test
    fun `given invalid url and invalid token when submit check url and token error message displayed`() =
        runTest {
            whenever(validateRegisterInput(any(), any())).thenReturn(false to false)

            subject.submit("http://example.com", "token")

            with(subject.viewState.latestValue(this)) {
                assertEquals(R.string.error_invalid_domain_name, urlErrorMsg)
                assertEquals(R.string.error_invalid_token_name, tokenErrorMsg)
            }
        }

    @Test
    fun `given url and token when submit check both are sensitized`() = runTest {
        val dirtyUrl = "http://example.com/   "
        val dirtyToken = "83726347623    "
        whenever(accountRepo.hasAccount(any(), any())).thenReturn(false)
        whenever(
            accountRepo.addAccount(
                any(),
                any(),
                any(),
            ),
        ).thenReturn(getAccountFixture(kFixture))
        whenever(validateRegisterInput(any(), any())).thenReturn(true to true)

        subject.submit(dirtyUrl, dirtyToken)

        verify(sanitiseRegisterInput).invoke(dirtyUrl, dirtyToken)
    }

    @Test
    fun `given correct url and token when submit check success message display`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { testScope, flowTurbine ->
            val savedAccount = getAccountFixture(kFixture)
            whenever(validateRegisterInput(any(), any())).thenReturn(true to true)
            whenever(accountRepo.hasAccount(any(), any())).thenReturn(false)
            whenever(accountRepo.addAccount(any(), any(), any())).thenReturn(savedAccount)

            subject.submit("http://example.com/", "83726347623")

            assertFalse(subject.viewState.latestValue(testScope).enableAddAccountBtn)
            assertEquals(
                HandleAuthSuccess(savedAccount.localId, savedAccount.name),
                flowTurbine.awaitItem(),
            )
        }

    @Test
    fun `given correct url and token when submit check add account button stays disabled`() =
        runTest {
            val account = getAccountFixture(kFixture)
            whenever(validateRegisterInput(any(), any())).thenReturn(true to true)
            whenever(accountRepo.hasAccount(any(), any())).thenReturn(false)
            whenever(accountRepo.addAccount(any(), any(), any())).thenReturn(account)

            subject.submit("http://example.com/", "83726347623")

            assertFalse(subject.viewState.latestValue(this).enableAddAccountBtn)
        }

    @Test
    fun `given correct url and token when submit check success check account info cached to db`() =
        runTest {
            val account = getAccountFixture(kFixture)
            whenever(validateRegisterInput(any(), any())).thenReturn(true to true)
            whenever(accountRepo.hasAccount(any(), any())).thenReturn(false)
            whenever(accountRepo.addAccount(any(), any(), any())).thenReturn(account)

            subject.submit(kFixture(), kFixture())
            advanceUntilIdle()

            verify(accountRepo).addAccount(
                navArgs.selectedCI.type,
                sanitisedUrl.toUrl(),
                sanitisedToken.toToken(),
            )
        }

    @Test
    fun `given account already added when submit check account already added message displayed`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            val account = getAccountFixture(kFixture)
            whenever(validateRegisterInput(any(), any())).thenReturn(true to true)
            whenever(accountRepo.hasAccount(any(), any())).thenReturn(true)
            whenever(accountRepo.addAccount(any(), any(), any())).thenReturn(account)

            subject.submit("http://example.com/", "83726347623")

            assertEquals(AccountAlreadyAdded, flowTurbine.awaitItem())
        }

    @Test
    fun `given account already added when submit check add account button becomes enabled`() =
        runTest {
            val account = getAccountFixture(kFixture)
            whenever(validateRegisterInput(any(), any())).thenReturn(true to true)
            whenever(accountRepo.hasAccount(any(), any())).thenReturn(true)
            whenever(accountRepo.addAccount(any(), any(), any())).thenReturn(account)

            subject.submit("http://example.com/", "83726347623")

            assertTrue(subject.viewState.latestValue(this).enableAddAccountBtn)
        }

    @Test
    fun `given failure saving account to db when submit check error message display and add account button enabled`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { testScope, flowTurbine ->
            whenever(validateRegisterInput(any(), any())).thenReturn(true to true)
            whenever(accountRepo.hasAccount(any(), any())).thenReturn(false)
            whenever(accountRepo.addAccount(any(), any(), any()))
                .thenThrow(IllegalStateException("Error"))

            subject.submit("http://example.com/", "83726347623")

            assertTrue(subject.viewState.latestValue(testScope).enableAddAccountBtn)
            assertEquals(ShowErrorAddingAccount(displayError), flowTurbine.awaitItem())
        }

    @Test
    fun `given failure checking duplicate accounts db when submit check error message display and add account button enabled`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { testScope, flowTurbine ->
            val account = getAccountFixture(kFixture)
            whenever(validateRegisterInput(any(), any())).thenReturn(true to true)
            whenever(accountRepo.hasAccount(any(), any())).thenThrow(IllegalStateException("Error"))
            whenever(accountRepo.addAccount(any(), any(), any())).thenReturn(account)

            subject.submit("http://example.com/", "83726347623")

            assertTrue(subject.viewState.latestValue(testScope).enableAddAccountBtn)
            assertEquals(ShowErrorAddingAccount(displayError), flowTurbine.awaitItem())
        }

    @Test
    fun `given credentials when submit check add account button disabled while submitting`() =
        runTest {
            val account = getAccountFixture(kFixture)
            whenever(validateRegisterInput(any(), any())).thenReturn(true to true)
            whenever(accountRepo.hasAccount(any(), any())).thenReturn(false)
            whenever(accountRepo.addAccount(any(), any(), any())).thenReturn(account)

            subject.submit("http://example.com/", "83726347623")

            assertFalse(subject.viewState.latestValue(this).enableAddAccountBtn)
        }
}

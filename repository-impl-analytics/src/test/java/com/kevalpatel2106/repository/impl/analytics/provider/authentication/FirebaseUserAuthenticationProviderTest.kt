package com.kevalpatel2106.repository.impl.analytics.provider.authentication

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kevalpatel2106.repository.impl.analytics.provider.authentication.FirebaseUserAuthenticationProvider
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class FirebaseUserAuthenticationProviderTest {
    private val taskResult = mock<Task<AuthResult>> {
        on { isSuccessful } doReturn true
    }
    private val firebaseAuth = mock<FirebaseAuth> {
        on { signInAnonymously() } doReturn taskResult
    }
    private val subject = FirebaseUserAuthenticationProvider(firebaseAuth)

    @Test
    fun `given firebase user not null when authenticating user then verify no authentication happen`() {
        val firebaseUser = mock<FirebaseUser>()
        whenever(firebaseAuth.currentUser).thenReturn(firebaseUser)

        subject.authenticate()

        verify(firebaseAuth, never()).signInAnonymously()
    }

    @Test
    fun `given firebase user null when authenticating user then verify authentication happen`() {
        whenever(firebaseAuth.currentUser).thenReturn(null)
        whenever(firebaseAuth.signInAnonymously()).thenReturn(taskResult)

        subject.authenticate()

        verify(firebaseAuth).signInAnonymously()
    }
}

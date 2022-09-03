package com.kevalpatel2106.repository.impl.analytics.usecase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

internal class FirebaseAuthenticateUserImplTest {
    private val taskResult = mock<Task<AuthResult>> {
        on { isSuccessful } doReturn true
    }
    private val firebaseAuth = mock<FirebaseAuth> {
        on { signInAnonymously() } doReturn taskResult
    }

    @Test
    fun `given debug build when authenticating user then verify no authentication happen`() {
        val subject = getSubject(true)

        subject()

        verifyNoInteractions(firebaseAuth)
    }

    @Test
    fun `given non debug build and firebase user not null when authenticating user then verify no authentication happen`() {
        val subject = getSubject(false)
        val firebaseUser = mock<FirebaseUser>()
        whenever(firebaseAuth.currentUser).thenReturn(firebaseUser)

        subject()

        verify(firebaseAuth, never()).signInAnonymously()
    }

    @Test
    fun `given non debug build and firebase user null when authenticating user then verify authentication happen`() {
        val subject = getSubject(false)
        whenever(firebaseAuth.currentUser).thenReturn(null)
        whenever(firebaseAuth.signInAnonymously()).thenReturn(taskResult)

        subject()

        verify(firebaseAuth).signInAnonymously()
    }

    private fun getSubject(isDebug: Boolean) = FirebaseAuthenticateUserImpl(isDebug, firebaseAuth)
}

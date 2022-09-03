package com.kevalpatel2106.repository.impl.analytics.usecase

import com.google.firebase.auth.FirebaseAuth
import com.kevalpatel2106.repository.di.IsDebug
import timber.log.Timber
import javax.inject.Inject

internal class FirebaseAuthenticateUserImpl @Inject constructor(
    @IsDebug private val isDebug: Boolean,
    private val firebaseAuth: FirebaseAuth,
) : FirebaseAuthenticateUser {

    override operator fun invoke() {
        if (isDebug) return

        if (firebaseAuth.currentUser == null) {
            firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Firebase Auth: User authenticated!")
                } else {
                    Timber.w("Firebase Auth: User authentication failed!")
                    Timber.e(task.exception)
                }
            }
        } else {
            Timber.d("Firebase Auth: User already authenticated.")
        }
    }
}

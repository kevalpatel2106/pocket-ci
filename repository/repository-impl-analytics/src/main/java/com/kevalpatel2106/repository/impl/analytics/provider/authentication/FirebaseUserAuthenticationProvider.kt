package com.kevalpatel2106.repository.impl.analytics.provider.authentication

import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import javax.inject.Inject

internal class FirebaseUserAuthenticationProvider @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : UserAuthenticationProvider {

    override fun authenticate() {
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

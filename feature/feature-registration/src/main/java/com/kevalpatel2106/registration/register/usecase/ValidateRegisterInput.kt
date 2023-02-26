package com.kevalpatel2106.registration.register.usecase

interface ValidateRegisterInput {
    operator fun invoke(url: String, token: String): Pair<Boolean, Boolean>
}

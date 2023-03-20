package com.kevalpatel2106.registration.register.usecase

internal interface ValidateRegisterInput {
    operator fun invoke(url: String, token: String): Pair<Boolean, Boolean>
}

package com.kevalpatel2106.registration.register.usecase

internal interface SanitiseRegisterInput {
    operator fun invoke(inputUrl: String, inputToken: String): Pair<String, String>
}

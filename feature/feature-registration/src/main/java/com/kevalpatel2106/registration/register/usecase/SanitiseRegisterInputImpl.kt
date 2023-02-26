package com.kevalpatel2106.registration.register.usecase

import javax.inject.Inject

class SanitiseRegisterInputImpl @Inject constructor() : SanitiseRegisterInput {

    override operator fun invoke(inputUrl: String, inputToken: String): Pair<String, String> {
        return inputUrl.trim() to inputToken.trim()
    }
}

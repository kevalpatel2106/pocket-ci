package com.kevalpatel2106.registration.register.usecase

import com.kevalpatel2106.entity.toUrl
import java.net.MalformedURLException
import javax.inject.Inject

internal class ValidateRegisterInputImpl @Inject constructor() : ValidateRegisterInput {

    override operator fun invoke(url: String, token: String): Pair<Boolean, Boolean> {
        @SuppressWarnings("SwallowedException")
        val isValidUrl = try {
            url.toUrl()
            url.endsWith("/")
        } catch (e: MalformedURLException) {
            false
        }
        val isValidToken = token.isNotBlank() && token == token.trim()
        return isValidUrl to isValidToken
    }
}

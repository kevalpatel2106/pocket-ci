package com.kevalpatel2106.connector.github.usecase

import com.kevalpatel2106.entity.Token
import javax.inject.Inject

internal class TokenHeaderValueBuilderImpl @Inject constructor() : TokenHeaderValueBuilder {

    override fun invoke(token: Token) = "Bearer ${token.getValue()}"
}

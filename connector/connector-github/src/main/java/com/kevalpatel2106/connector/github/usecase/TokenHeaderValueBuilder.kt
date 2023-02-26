package com.kevalpatel2106.connector.github.usecase

import com.kevalpatel2106.entity.Token

interface TokenHeaderValueBuilder {
    operator fun invoke(token: Token): String
}

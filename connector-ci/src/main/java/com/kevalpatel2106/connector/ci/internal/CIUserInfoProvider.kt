package com.kevalpatel2106.connector.ci.internal

import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url

interface CIUserInfoProvider {
    suspend fun getMyAccountInfo(url: Url, token: Token): Account
}

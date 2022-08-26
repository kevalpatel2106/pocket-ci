package com.kevalpatel2106.connector.bitrise

import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.network.mapper.AccountMapper
import com.kevalpatel2106.connector.ci.internal.CIUserInfoProvider
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import javax.inject.Inject

internal class BitriseUserInfoProvider @Inject constructor(
    private val retrofitClient: BitriseRetrofitClient,
    private val accountMapper: AccountMapper,
) : CIUserInfoProvider {

    override suspend fun getMyAccountInfo(url: Url, token: Token): Account {
        val response = retrofitClient
            .getBitriseService(baseUrl = url, token = token)
            .getMe()
        requireNotNull(response.data)
        return accountMapper(response.data, url, token)
    }
}

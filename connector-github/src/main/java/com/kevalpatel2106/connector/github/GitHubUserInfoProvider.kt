package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.internal.CIUserInfoProvider
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.mapper.AccountMapper
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import javax.inject.Inject

internal class GitHubUserInfoProvider @Inject constructor(
    private val retrofitClient: GitHubRetrofitClient,
    private val accountMapper: AccountMapper,
) : CIUserInfoProvider {

    override suspend fun getMyAccountInfo(url: Url, token: Token): Account {
        val userDto = retrofitClient
            .getService(baseUrl = url, token = token)
            .getUser()
        return accountMapper(userDto, url, token)
    }
}

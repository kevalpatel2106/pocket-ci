package com.kevalpatel2106.connector.github.network

import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.connector.github.network.interceptor.AuthHeaderInterceptor
import com.kevalpatel2106.connector.github.usecase.TokenHeaderValueBuilder
import com.kevalpatel2106.coreNetwork.BaseRetrofitClient
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import javax.inject.Inject

internal class GitHubRetrofitClient @Inject constructor(
    okHttpClientFactory: OkHttpClientFactory,
    private val tokenHeaderValueBuilder: TokenHeaderValueBuilder
) : BaseRetrofitClient(okHttpClientFactory) {

    override fun interceptors(baseUrl: Url, token: Token) = listOf(
        AuthHeaderInterceptor.create(token, tokenHeaderValueBuilder)
    )

    fun getService(baseUrl: Url, token: Token) = getService(
        baseUrl = baseUrl,
        token = token,
        service = GitHubEndpoint::class.java,
    )
}

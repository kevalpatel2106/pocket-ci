package com.kevalpatel2106.connector.github.network

import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.connector.github.network.interceptor.AuthHeaderInterceptor
import com.kevalpatel2106.coreNetwork.BaseRetrofitClient
import com.kevalpatel2106.coreNetwork.moshi.MoshiFactory
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import javax.inject.Inject

internal class GitHubRetrofitClient @Inject constructor(
    okHttpClientFactory: OkHttpClientFactory,
    moshiFactory: MoshiFactory,
) : BaseRetrofitClient(okHttpClientFactory, moshiFactory) {

    override fun interceptors(baseUrl: String, token: String?) =
        listOf(AuthHeaderInterceptor.create(token))

    fun getService(baseUrl: Url, token: Token?) = getService(
        baseUrl = baseUrl.value,
        token = token?.getValue(),
        service = GitHubEndpoint::class.java,
    )
}

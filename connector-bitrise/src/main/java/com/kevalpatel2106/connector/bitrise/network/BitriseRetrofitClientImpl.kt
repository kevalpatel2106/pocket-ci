package com.kevalpatel2106.connector.bitrise.network

import com.kevalpatel2106.connector.bitrise.network.endpoint.BitriseEndpoint
import com.kevalpatel2106.connector.bitrise.network.interceptor.AuthHeaderInterceptor
import com.kevalpatel2106.connector.bitrise.network.interceptor.VersionNameInterceptor
import com.kevalpatel2106.coreNetwork.BaseRetrofitClient
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import javax.inject.Inject

internal class BitriseRetrofitClientImpl @Inject constructor(
    okHttpClientFactory: OkHttpClientFactory,
) : BaseRetrofitClient(okHttpClientFactory), BitriseRetrofitClient {

    override fun interceptors(baseUrl: Url, token: Token) = listOf(
        AuthHeaderInterceptor.create(token),
        VersionNameInterceptor.create(),
    )

    override fun getBitriseService(baseUrl: Url, token: Token) = getService(
        baseUrl = baseUrl,
        token = token,
        service = BitriseEndpoint::class.java,
    )
}

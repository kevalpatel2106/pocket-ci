package com.kevalpatel2106.connector.bitrise.network.interceptor

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

internal class VersionNameInterceptor private constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
        val versionedUrl = url.toString().replace(url.host, "${url.host}/$BITRISE_API_VERSION")
        return chain.proceed(
            request.newBuilder()
                .url(versionedUrl.toHttpUrlOrNull() ?: request.url)
                .build(),
        )
    }

    companion object {
        private const val BITRISE_API_VERSION = "v0.1"
        fun create() = VersionNameInterceptor()
    }
}

package com.kevalpatel2106.connector.bitrise.network.interceptor

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

internal class VersionNameInterceptor private constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val apiVersion = request.header(API_VERSION_HEADER_KEY)

        val finalUrl = if (apiVersion == null) {
            request.url
        } else {
            val host = request.url.host
            request.url.toString()
                .replace(host, "$host/$apiVersion")
                .toHttpUrlOrNull() ?: request.url
        }
        return chain.proceed(
            request.newBuilder()
                .removeHeader(API_VERSION_HEADER_KEY)
                .url(finalUrl)
                .build(),
        )
    }

    companion object {
        const val API_VERSION_HEADER_KEY = "api-version"
        const val BITRISE_API_VERSION = "v0.1"
        fun create() = VersionNameInterceptor()
    }
}

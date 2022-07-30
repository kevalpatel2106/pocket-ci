package com.kevalpatel2106.connector.bitrise.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class HeadersInterceptor private constructor(private val token: String?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder().apply {
        }
        return chain.proceed(builder.build())
    }

    companion object {

        fun create(token: String?) = HeadersInterceptor(token)
    }
}

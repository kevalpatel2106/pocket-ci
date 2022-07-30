package com.kevalpatel2106.connector.github.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class HeadersInterceptor private constructor(private val token: String?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder().apply {
            header(ACCEPT_HEADER_KEY, ACCEPT_HEADER_VALUE)
            if (request.header(ADD_AUTH) != null && token != null) {
                header(AUTHENTICATION, "token $token").removeHeader(ADD_AUTH)
            }
        }
        return chain.proceed(builder.build())
    }

    companion object {
        internal const val ADD_AUTH = "Add-Auth"
        private const val API_VERSION = "v3"
        private const val AUTHENTICATION = "Authorization"
        private const val ACCEPT_HEADER_KEY = "Accept"
        private const val ACCEPT_HEADER_VALUE = "application/vnd.github.$API_VERSION+json"

        fun create(token: String?) = HeadersInterceptor(token)
    }
}

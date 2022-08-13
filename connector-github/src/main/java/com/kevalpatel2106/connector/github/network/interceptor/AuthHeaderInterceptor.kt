package com.kevalpatel2106.connector.github.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class AuthHeaderInterceptor private constructor(private val token: String?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder().apply {
            if (request.header(ADD_AUTH_KEY) != null && token != null) {
                header(AUTHENTICATION, "token $token").removeHeader(ADD_AUTH_KEY)
            }
        }
        return chain.proceed(builder.build())
    }

    companion object {
        private const val AUTHENTICATION = "Authorization"
        internal const val ADD_AUTH_KEY = "Add-Auth"

        fun create(token: String?) = AuthHeaderInterceptor(token)
    }
}

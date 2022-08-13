package com.kevalpatel2106.connector.bitrise.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class AuthHeaderInterceptor private constructor(private val token: String?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder().apply {
            if (request.header(ADD_AUTH_KEY) != null && token != null) {
                header(AUTHENTICATION, token).removeHeader(ADD_AUTH_KEY)
            }
        }
        return chain.proceed(builder.build())
    }

    companion object {
        const val ADD_AUTH_KEY = "Add-Auth"
        private const val AUTHENTICATION = "Authorization"

        fun create(token: String?) = AuthHeaderInterceptor(token)
    }
}

package com.kevalpatel2106.connector.bitrise.network.interceptor

import com.kevalpatel2106.entity.Token
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthHeaderInterceptor private constructor(private val token: Token) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder().apply {
            if (request.header(ADD_AUTH_KEY) != null) {
                header(AUTHENTICATION, token.getValue()).removeHeader(ADD_AUTH_KEY)
            }
        }
        return chain.proceed(builder.build())
    }

    companion object {
        const val ADD_AUTH_KEY = "Add-Auth"
        private const val AUTHENTICATION = "Authorization"

        fun create(token: Token) = AuthHeaderInterceptor(token)
    }
}

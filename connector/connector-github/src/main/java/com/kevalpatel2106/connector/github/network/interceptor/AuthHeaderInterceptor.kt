package com.kevalpatel2106.connector.github.network.interceptor

import com.kevalpatel2106.connector.github.usecase.TokenHeaderValueBuilder
import com.kevalpatel2106.entity.Token
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthHeaderInterceptor private constructor(
    private val token: Token,
    private val tokenHeaderValueBuilder: TokenHeaderValueBuilder,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder().apply {
            if (request.header(ADD_AUTH_KEY) != null) {
                header(AUTHENTICATION, tokenHeaderValueBuilder(token)).removeHeader(ADD_AUTH_KEY)
            }
        }
        return chain.proceed(builder.build())
    }

    companion object {
        internal const val AUTHENTICATION = "Authorization"
        internal const val ADD_AUTH_KEY = "Add-Auth"

        fun create(token: Token, tokenHeaderValueBuilder: TokenHeaderValueBuilder) =
            AuthHeaderInterceptor(token, tokenHeaderValueBuilder)
    }
}

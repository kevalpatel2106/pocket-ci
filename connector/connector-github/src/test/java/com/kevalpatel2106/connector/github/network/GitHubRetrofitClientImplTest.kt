package com.kevalpatel2106.connector.github.network

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.interceptor.AuthHeaderInterceptor
import com.kevalpatel2106.connector.github.usecase.TokenHeaderValueBuilder
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.Token
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

internal class GitHubRetrofitClientImplTest {
    private val fixture = KFixture()
    private val okHttpClientFactory = mock<OkHttpClientFactory>()
    private val tokenHeaderValueBuilder = mock<TokenHeaderValueBuilder>()
    private val subject = GitHubRetrofitClientImpl(okHttpClientFactory, tokenHeaderValueBuilder)

    @Test
    fun `given base url and token verify auth interceptor added to interceptors list`() {
        val baseUrl = getUrlFixture(fixture)
        val token = fixture<Token>()

        val actual = subject.interceptors(baseUrl, token)

        assertTrue(actual.first() is AuthHeaderInterceptor)
    }
}

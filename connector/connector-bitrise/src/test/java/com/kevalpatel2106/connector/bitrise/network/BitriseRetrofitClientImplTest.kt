package com.kevalpatel2106.connector.bitrise.network

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.interceptor.AuthHeaderInterceptor
import com.kevalpatel2106.connector.bitrise.network.interceptor.VersionNameInterceptor
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.Token
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

internal class BitriseRetrofitClientImplTest {
    private val fixture = KFixture()
    private val okHttpClientFactory = mock<OkHttpClientFactory>()
    private val subject = BitriseRetrofitClientImpl(okHttpClientFactory)

    @Test
    fun `given base url and token verify auth interceptor added first`() {
        val baseUrl = getUrlFixture(fixture)
        val token = fixture<Token>()

        val actual = subject.interceptors(baseUrl, token)

        assertTrue(actual.first() is AuthHeaderInterceptor)
    }

    @Test
    fun `given base url and token verify version name interceptor added second`() {
        val baseUrl = getUrlFixture(fixture)
        val token = fixture<Token>()

        val actual = subject.interceptors(baseUrl, token)

        assertTrue(actual[1] is VersionNameInterceptor)
    }
}

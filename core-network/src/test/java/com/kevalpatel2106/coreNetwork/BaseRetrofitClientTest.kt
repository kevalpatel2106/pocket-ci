package com.kevalpatel2106.coreNetwork

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreNetwork.converter.StringConverter
import com.kevalpatel2106.coreNetwork.okHttp.FlavouredInterceptor
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.converter.moshi.MoshiConverterFactory

internal class BaseRetrofitClientTest {
    private val fixture = KFixture()
    private val flavouredInterceptors = listOf(Interceptor { it.proceed(it.request()) })
    private val flavouredNetworkInterceptors = listOf(Interceptor { it.proceed(it.request()) })
    private val flavouredInterceptorList = object : FlavouredInterceptor {
        override fun getInterceptors() = flavouredInterceptors
        override fun getNetworkInterceptors() = flavouredNetworkInterceptors
    }
    private val okHttpClientFactory = mock<OkHttpClientFactory> {
        on { getOkHttpClient() } doReturn OkHttpClient()
        on { getFlavouredInterceptor() } doReturn flavouredInterceptorList
    }

    private val interceptors = listOf(Interceptor { it.proceed(it.request()) })
    private val subject = object : BaseRetrofitClient(okHttpClientFactory) {
        override fun interceptors(baseUrl: Url, token: Token): List<Interceptor> = interceptors
        fun getRetrofitExposed(baseUrl: Url, token: Token) = getRetrofit(baseUrl, token)
    }

    @Test
    fun `when retrofit client built then check string and moshi converter added in an order`() {
        val actual =
            subject.getRetrofitExposed(getUrlFixture(fixture), fixture()).converterFactories()

        assertTrue(actual.any { it is MoshiConverterFactory })
        assertTrue(actual.any { it is StringConverter })
    }

    @Test
    fun `given base url when retrofit client built then verify base url`() {
        val baseUrl = getUrlFixture(fixture)

        val actual = subject.getRetrofitExposed(baseUrl, fixture()).baseUrl()

        assertEquals(baseUrl.value, actual.toString())
    }

    @Test
    fun `when retrofit client built then verify no additional call adapters added`() {
        val actual = subject.getRetrofitExposed(getUrlFixture(fixture), fixture())
            .callAdapterFactories()

        val expectedDefaultCallAdapters = 2
        assertEquals(expectedDefaultCallAdapters, actual.size)
    }
}

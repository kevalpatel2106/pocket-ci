package com.kevalpatel2106.connector.bitrise.network

import com.kevalpatel2106.connector.bitrise.network.endpoint.BitriseEndpoint
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url

internal interface BitriseRetrofitClient {
    fun getBitriseService(baseUrl: Url, token: Token): BitriseEndpoint
}

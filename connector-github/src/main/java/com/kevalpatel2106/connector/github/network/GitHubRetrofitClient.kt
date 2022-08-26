package com.kevalpatel2106.connector.github.network

import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.Url

internal interface GitHubRetrofitClient {
    fun getGithubService(baseUrl: Url, token: Token): GitHubEndpoint
}

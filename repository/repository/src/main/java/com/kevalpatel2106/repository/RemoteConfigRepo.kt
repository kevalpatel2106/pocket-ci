package com.kevalpatel2106.repository

import com.kevalpatel2106.entity.Url

interface RemoteConfigRepo {
    fun getPrivacyPolicy(): Url
    fun getChangelog(): Url
}

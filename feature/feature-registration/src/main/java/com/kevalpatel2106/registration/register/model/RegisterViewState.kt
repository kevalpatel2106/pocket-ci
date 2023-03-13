package com.kevalpatel2106.registration.register.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.Url

internal data class RegisterViewState(
    @StringRes val ciName: Int,
    @DrawableRes val ciIcon: Int,
    val ciInfoUrl: Url,
    val defaultUrl: Url,
    val enableAddAccountBtn: Boolean,
    val allowUrlEdit: Boolean,
    val sampleAuthToken: String,
    val authTokenHintLink: String,
    @StringRes val urlErrorMsg: Int?,
    @StringRes val tokenErrorMsg: Int?,
) {
    companion object {
        fun initialState(ciInfo: CIInfo) = RegisterViewState(
            ciName = ciInfo.name,
            ciIcon = ciInfo.icon,
            ciInfoUrl = ciInfo.infoUrl,
            enableAddAccountBtn = true,
            urlErrorMsg = null,
            tokenErrorMsg = null,
            sampleAuthToken = ciInfo.sampleAuthToken.getValue(),
            defaultUrl = ciInfo.defaultBaseUrl,
            allowUrlEdit = ciInfo.supportCustomBaseUrl,
            authTokenHintLink = ciInfo.authTokenExplainLink.value,
        )
    }
}

package com.kevalpatel2106.entity

import android.os.Parcelable
import com.kevalpatel2106.entity.id.AccountId
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountBasic(
    val localId: AccountId,
    val baseUrl: Url,
    val authToken: Token,
    val type: CIType,
) : Parcelable {

    init {
        // Validate base url for retrofit
        assert(baseUrl.value.endsWith("/")) { "Base URL $baseUrl should end with /." }
    }
}

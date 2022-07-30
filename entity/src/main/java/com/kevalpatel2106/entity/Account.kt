package com.kevalpatel2106.entity

import android.os.Parcelable
import com.kevalpatel2106.entity.id.AccountId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Account(
    val localId: AccountId,
    val name: String,
    val email: String,
    val avatar: Url?,
    val type: CIType,
    val isSelected: Boolean,
    val baseUrl: Url,
    val authToken: Token,
) : Parcelable {
    init {
        // Validate base url for retrofit
        assert(baseUrl.value.endsWith("/")) { "Base URL $baseUrl should end with /." }
    }
}

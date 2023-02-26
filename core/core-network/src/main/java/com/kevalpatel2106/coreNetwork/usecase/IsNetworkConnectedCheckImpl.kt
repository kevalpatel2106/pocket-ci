package com.kevalpatel2106.coreNetwork.usecase

import android.net.ConnectivityManager
import javax.inject.Inject

class IsNetworkConnectedCheckImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager,
) : IsNetworkConnectedCheck {

    override fun invoke(): Boolean {
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}

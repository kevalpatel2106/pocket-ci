package com.kevalpatel2106.core.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import timber.log.Timber

fun NavController.navigateToInAppDeeplink(
    destination: DeepLinkDestinations,
    cleanUpStack: Boolean = false,
) {
    val uri = Uri.Builder()
        .scheme(URI_SCHEMA)
        .authority(destination.value)
        .apply {
            destination.navArgs.forEach { (key, value) ->
                appendQueryParameter(key, value.orEmpty())
            }
        }
        .build()
    Timber.i("Navigating deeplink: $uri")
    if (cleanUpStack) {
        val homeNavGraphResourceId = context.resources.getIdentifier(
            SPLASH_ID,
            RESOURCE_ID,
            context.packageName,
        )
        val navigationOptions = NavOptions.Builder()
            .setPopUpTo(destinationId = homeNavGraphResourceId, inclusive = true)
            .build()
        navigate(uri, navigationOptions)
    } else {
        navigate(uri)
    }
}

private const val SPLASH_ID = "splash"
private const val RESOURCE_ID = "id"
private const val URI_SCHEMA = "pocket-ci"

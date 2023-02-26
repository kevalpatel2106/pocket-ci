package com.kevalpatel2106.core.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.kevalpatel2106.core.R
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
    val navigationOptions = NavOptions.Builder()
        .setLaunchSingleTop(true)
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .apply {
            if (cleanUpStack) {
                val mainNavGraphId = context.resources.getIdentifier(
                    MAIN_NAV_GRAPH_ID,
                    RESOURCE_ID,
                    context.packageName,
                )
                setPopUpTo(mainNavGraphId, inclusive = true)
            }
        }
        .build()
    navigate(uri, navigationOptions)
}

private const val MAIN_NAV_GRAPH_ID = "nav_graph" // From nav_graph.xml
private const val RESOURCE_ID = "id"
private const val URI_SCHEMA = "pocket-ci"

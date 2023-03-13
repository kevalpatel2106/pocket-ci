package com.kevalpatel2106.feature.drawer.drawer.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.kevalpatel2106.core.resources.R

internal enum class BottomDrawerItem(
    val icon: ImageVector,
    @StringRes val title: Int,
) {

    Settings(
        icon = Icons.Filled.Settings,
        title = R.string.navigation_title_settings,
    ),

    LinkedAccounts(
        icon = Icons.Filled.Person,
        title = R.string.navigation_title_accounts,
    )
}

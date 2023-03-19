package com.kevalpatel2106.feature.account.list.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.element.UrlImage
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_HUGE
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_LARGE
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.entity.Url

@Composable
internal fun AccountAvatar(
    accountUrl: Url?,
    @StringRes ciName: Int,
    @DrawableRes ciIcon: Int,
    modifier: Modifier = Modifier
) = Box(
    modifier = modifier.padding(SPACING_SMALL)
) {
    UrlImage(
        url = accountUrl,
        contentDescription = stringResource(id = ciName),
        contentScale = ContentScale.Fit,
        placeHolder = R.drawable.ic_account_placeholder,
        modifier = Modifier
            .width(SPACING_HUGE)
            .height(SPACING_HUGE)
            .clip(CircleShape)
    )
    Image(
        painter = painterResource(id = ciIcon),
        contentDescription = stringResource(id = ciName),
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .width(SPACING_LARGE)
            .height(SPACING_LARGE)
            .align(Alignment.BottomEnd)
            .clip(CircleShape),
    )
}

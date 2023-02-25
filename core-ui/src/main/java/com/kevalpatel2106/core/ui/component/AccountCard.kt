package com.kevalpatel2106.core.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.entity.Url

@Composable
fun AccountInfoCard(
    avatar: Url?,
    name: String,
    email: String,
    @StringRes ciName: Int,
    @DrawableRes ciIcon: Int,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    showDeleteIcon: Boolean = false,
    onItemClick: () -> Unit = {},
    onItemDelete: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .padding(vertical = SPACING_SMALL)
            .clickable { onItemClick() },
        border = if (isSelected) {
            BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
        } else {
            null
        },
    ) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (avatarRef, infoViewRef, deleteIconRef) = createRefs()

            AccountAvatar(
                accountUrl = avatar,
                ciName = ciName,
                ciIcon = ciIcon,
                modifier = Modifier.constrainAs(avatarRef) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start)
                },
            )
            AccountInfoItemView(
                name = name,
                email = email,
                modifier = Modifier
                    .padding(start = SPACING_SMALL)
                    .constrainAs(infoViewRef) {
                        centerVerticallyTo(parent)
                        start.linkTo(avatarRef.end)
                        if (showDeleteIcon) {
                            end.linkTo(deleteIconRef.start)
                        } else {
                            end.linkTo(parent.end)
                        }
                        width = Dimension.fillToConstraints
                    },
            )
            if (showDeleteIcon) {
                IconButton(
                    onClick = onItemDelete,
                    modifier = Modifier.constrainAs(deleteIconRef) {
                        centerVerticallyTo(parent)
                        end.linkTo(parent.end)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(id = R.string.delete),
                    )
                }
            }
        }
    }
}

@Composable
private fun AccountInfoItemView(
    name: String,
    email: String,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    Text(
        text = name,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.bodyLarge,
    )
    Spacer(modifier = Modifier.padding(top = SPACING_SMALL))
    Text(
        text = email,
        style = MaterialTheme.typography.bodyMedium,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}
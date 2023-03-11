package com.kevalpatel2106.core.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.PIXEL_C
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.PocketCITheme
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_MICRO
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.entity.Url

@Composable
fun ProjectCard(
    projectImageUrl: Url?,
    projectOwner: String,
    projectName: String,
    isPinned: Boolean,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = {},
    onItemPinned: (Boolean) -> Unit = {},
) {
    Card(
        modifier = modifier.clickable(isEnabled) { onItemClick() },
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SPACING_SMALL),
        ) {
            val (imageRef, pinCheckboxRef, projectInfoRef) = createRefs()

            UrlImage(
                url = projectImageUrl,
                contentDescription = projectName,
                placeHolder = R.drawable.ic_repository_placeholder,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(imageRef) {
                        centerVerticallyTo(parent)
                        start.linkTo(parent.start)
                    }
                    .width(Spacing.SPACING_XXLARGE)
                    .height(Spacing.SPACING_XXLARGE)
                    .clip(CircleShape),
            )

            ProjectInfo(
                projectOwner = projectOwner, projectName = projectName,
                modifier = Modifier.constrainAs(projectInfoRef) {
                    start.linkTo(imageRef.end)
                    end.linkTo(pinCheckboxRef.start)
                    centerVerticallyTo(parent)
                    width = Dimension.fillToConstraints
                },
            )

            val pinButtonContentDescription = if (isPinned) {
                R.string.pin_project_button_content_description
            } else {
                R.string.unpin_project_button_content_description
            }
            CheckboxWithIcon(
                checkedIcon = Icons.Filled.PushPin,
                unCheckedIcon = Icons.Outlined.PushPin,
                isChecked = isPinned,
                contentDescription = stringResource(id = pinButtonContentDescription),
                onCheckChange = onItemPinned,
                modifier = Modifier.constrainAs(pinCheckboxRef) {
                    centerVerticallyTo(parent)
                    end.linkTo(parent.end)
                },
            )
        }
    }
}

@Composable
private fun ProjectInfo(
    projectOwner: String,
    projectName: String,
    modifier: Modifier
) = Column(
    verticalArrangement = Arrangement.Center,
    modifier = modifier.padding(start = SPACING_SMALL),
) {
    Text(
        text = projectOwner,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.titleSmall,
    )
    Spacer(modifier = Modifier.padding(top = SPACING_MICRO))
    Text(
        text = projectName,
        style = MaterialTheme.typography.bodyLarge,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}


@Composable
@Preview(
    name = "Normal",
    group = "Account Card",
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    group = "Account Card",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Large text",
    group = "Account Card",
    showSystemUi = true,
    showBackground = true,
    fontScale = 2f,
)
@Preview(
    name = "Tablet",
    group = "Account Card",
    showSystemUi = true,
    showBackground = true,
    device = PIXEL_C,
)
fun ProjectCardPreview() = PocketCITheme {
    Column {
        ProjectCard(
            projectImageUrl = Url.EMPTY,
            projectOwner = NAME,
            projectName = NAME,
            isPinned = false,
            isEnabled = false,
            modifier = Modifier.padding(vertical = SPACING_SMALL),
        )
        ProjectCard(
            projectImageUrl = Url.EMPTY,
            projectOwner = NAME,
            projectName = NAME,
            isPinned = true,
            isEnabled = false,
            modifier = Modifier.padding(vertical = SPACING_SMALL),
        )
        ProjectCard(
            projectImageUrl = Url.EMPTY,
            projectOwner = NAME,
            projectName = NAME,
            isPinned = true,
            isEnabled = false,
            modifier = Modifier.padding(vertical = SPACING_SMALL),
        )
        ProjectCard(
            projectImageUrl = Url.EMPTY,
            projectOwner = LONG_STRING,
            projectName = LONG_STRING,
            isPinned = true,
            isEnabled = false,
            modifier = Modifier.padding(vertical = SPACING_SMALL),
        )
        ProjectCard(
            projectImageUrl = Url.EMPTY,
            projectOwner = LONG_STRING,
            projectName = LONG_STRING,
            isPinned = true,
            isEnabled = false,
            modifier = Modifier.padding(vertical = SPACING_SMALL),
        )
    }
}
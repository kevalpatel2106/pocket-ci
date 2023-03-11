package com.kevalpatel2106.core.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.kevalpatel2106.core.ui.PocketCITheme

@Composable
fun CheckboxWithIcon(
    checkedIcon: ImageVector,
    unCheckedIcon: ImageVector,
    contentDescription: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckChange: (isChecked: Boolean) -> Unit,
) {
    val icon by remember {
        val icon = if (isChecked) checkedIcon else unCheckedIcon
        mutableStateOf(icon)
    }
    IconButton(
        modifier = modifier,
        onClick = { onCheckChange(isChecked.not()) },
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
@Preview(
    name = "Normal",
    group = "CheckboxWithIcon",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    group = "CheckboxWithIcon",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Large text",
    group = "CheckboxWithIcon",
    showSystemUi = true,
    showBackground = true,
    fontScale = 2f,
)
@Preview(
    name = "Tablet",
    group = "CheckboxWithIcon",
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_C,
)
fun CheckboxWithIconPreview() = PocketCITheme {
    Column {
        CheckboxWithIcon(
            checkedIcon = Icons.Filled.PushPin,
            unCheckedIcon = Icons.Outlined.PushPin,
            isChecked = true,
            contentDescription = stringResource(id = SAMPLE_STRING_RES),
            onCheckChange = { },
        )
        CheckboxWithIcon(
            checkedIcon = Icons.Filled.PushPin,
            unCheckedIcon = Icons.Outlined.PushPin,
            isChecked = false,
            contentDescription = stringResource(id = SAMPLE_STRING_RES),
            onCheckChange = { },
        )
    }
}
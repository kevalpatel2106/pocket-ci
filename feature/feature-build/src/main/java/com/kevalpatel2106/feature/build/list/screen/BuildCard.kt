package com.kevalpatel2106.feature.build.list.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.Commit
import androidx.compose.material.icons.filled.DynamicForm
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HourglassFull
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.component.BuildStatusView
import com.kevalpatel2106.core.ui.component.ItemPropertyChip
import com.kevalpatel2106.core.ui.component.ItemPropertyView
import com.kevalpatel2106.core.ui.extension.LONG_STRING
import com.kevalpatel2106.core.ui.extension.NAME
import com.kevalpatel2106.core.ui.extension.SHORT_STRING
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.core.ui.theme.PocketCITheme
import com.kevalpatel2106.core.ui.theme.colorOrange
import com.kevalpatel2106.entity.BuildStatus
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.feature.build.list.model.BuildListItem.BuildItem

@Composable
internal fun BuildCard(
    buildItem: BuildItem,
    modifier: Modifier = Modifier,
    onItemClick: (item: BuildItem) -> Unit = {}
) = with(buildItem) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(this) },
    ) {
        Row(modifier = Modifier.padding(SPACING_SMALL)) {
            BuildStatusView(
                buildStatus = buildStatus,
                modifier = Modifier.alignByBaseline(),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = SPACING_SMALL),
            ) {
                Text(
                    text = buildItem.numberText,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.colorOrange,
                    style = MaterialTheme.typography.titleLarge,
                )
                if (commitMessage != null) {
                    Text(
                        text = commitMessage,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = Spacing.SPACING_MICRO)
                            .fillMaxWidth(),
                    )
                }
                BuildPropertiesView(this@with)
                BuildTimePropertiesView(this@with)
            }
        }
    }
}

@Composable
private fun BuildTimePropertiesView(buildItem: BuildItem) = with(buildItem) {
    ItemPropertyView(
        icon = Icons.Filled.History,
        label = R.string.build_list_triggered_time_label,
        value = triggeredTime,
    )
    ItemPropertyView(
        icon = Icons.Filled.HourglassFull,
        label = R.string.build_list_execution_time_label,
        value = executionTime,
    )
}

@Composable
private fun BuildPropertiesView(buildItem: BuildItem) = with(buildItem) {
    val horizontalScrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Spacing.SPACING_MICRO)
            .horizontalScroll(horizontalScrollState),
    ) {
        ItemPropertyChip(
            icon = Icons.Filled.DynamicForm,
            value = workflowName,
            contentDescription = R.string.builds_workflow_name_content_description,
        )
        ItemPropertyChip(
            icon = Icons.Filled.AccountTree,
            value = headBranch,
            contentDescription = R.string.builds_branch_content_description,
        )
        if (!commitHash.isNullOrBlank()) {
            ItemPropertyChip(
                icon = Icons.Filled.Commit,
                value = commitHash,
                contentDescription = R.string.builds_commit_hash_content_description,
            )
        }
        if (!triggeredBy.isNullOrBlank()) {
            ItemPropertyChip(
                icon = Icons.Filled.AdsClick,
                value = triggeredBy,
                contentDescription = R.string.builds_triggered_by_content_description,
            )
        }
    }
}

@Composable
@Preview(
    name = "Normal",
    group = "Job Card",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    group = "Job Card",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Large text",
    group = "Job Card",
    showSystemUi = true,
    showBackground = true,
    fontScale = 2f,
)
@Preview(
    name = "Tablet",
    group = "Job Card",
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_C,
)
private fun BuildCardPreview() = PocketCITheme {
    val item = BuildItem(
        buildId = BuildId("123"),
        buildStatus = BuildStatus.RUNNING,
        headBranch = "Main",
        commitMessage = SHORT_STRING,
        commitHash = "123456",
        numberText = "#123",
        workflowName = SHORT_STRING,
        triggeredBy = NAME,
        executionTime = "12 hours ago",
        triggeredTime = "1d 12h",
    )
    Column {
        BuildCard(item)
        Spacer(modifier = Modifier.height(SPACING_SMALL))
        BuildCard(
            item.copy(
                buildStatus = BuildStatus.SUCCESS,
                headBranch = LONG_STRING.take(20),
                commitMessage = LONG_STRING,
                commitHash = "123456",
                numberText = "#1234567890",
                workflowName = LONG_STRING,
                triggeredBy = LONG_STRING.take(20),
                executionTime = "12 minutes ago",
                triggeredTime = "1d 12h",
            ),
        )
        Spacer(modifier = Modifier.height(SPACING_SMALL))
        BuildCard(
            item.copy(
                buildStatus = BuildStatus.FAIL,
                headBranch = LONG_STRING.take(20),
                commitMessage = null,
                commitHash = null,
                triggeredBy = null,
            ),
        )
    }
}
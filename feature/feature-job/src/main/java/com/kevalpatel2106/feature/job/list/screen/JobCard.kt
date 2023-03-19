package com.kevalpatel2106.feature.job.list.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HourglassFull
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.core.ui.component.BuildStatusView
import com.kevalpatel2106.core.ui.component.ItemPropertyView
import com.kevalpatel2106.core.ui.extension.ARTIFACT_NAME
import com.kevalpatel2106.core.ui.extension.JOB_NAME
import com.kevalpatel2106.core.ui.extension.LONG_STRING
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.core.ui.theme.PocketCITheme
import com.kevalpatel2106.entity.BuildStatus

@Composable
internal fun JobCard(
    buildStatus: BuildStatus,
    jobName: String,
    executionTime: String? = null,
    triggerTime: String? = null,
    modifier: Modifier = Modifier,
) = Card(
    modifier = modifier,
) {
    Column(
        Modifier
            .padding(SPACING_SMALL)
            .fillMaxWidth(),
    ) {
        Row {
            BuildStatusView(buildStatus = buildStatus)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = SPACING_SMALL),
            ) {
                Text(
                    text = jobName,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                )
                if (triggerTime != null) {
                    ItemPropertyView(
                        icon = Icons.Filled.History,
                        label = R.string.job_list_execution_time_label,
                        value = triggerTime,
                    )
                }
                if (executionTime != null) {
                    ItemPropertyView(
                        icon = Icons.Filled.HourglassFull,
                        label = R.string.job_list_triggered_time_label,
                        value = executionTime,
                    )
                }
            }
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
fun JobCardPreview() = PocketCITheme {
    Column {
        BuildStatus.values().forEach { buildStatus ->
            JobCard(
                buildStatus = buildStatus,
                jobName = JOB_NAME,
                executionTime = if (buildStatus in listOf(BuildStatus.SKIPPED, BuildStatus.ABORT)) {
                    null
                } else {
                    "1d 12h"
                },
                triggerTime = if (buildStatus == BuildStatus.SKIPPED) null else "12 minutes ago",
            )
            Spacer(modifier = Modifier.height(SPACING_SMALL))
            JobCard(
                buildStatus = buildStatus,
                jobName = LONG_STRING,
                executionTime = if (buildStatus in listOf(BuildStatus.SKIPPED, BuildStatus.ABORT)) {
                    null
                } else {
                    "1h 12m"
                },
                triggerTime = if (buildStatus == BuildStatus.SKIPPED) null else "12 hours ago",
            )
            Spacer(modifier = Modifier.height(SPACING_SMALL))
        }
        JobCard(
            buildStatus = BuildStatus.SKIPPED,
            jobName = ARTIFACT_NAME,
        )
    }
}
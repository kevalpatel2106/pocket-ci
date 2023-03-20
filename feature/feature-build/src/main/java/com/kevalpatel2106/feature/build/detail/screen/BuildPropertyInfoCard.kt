package com.kevalpatel2106.feature.build.detail.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HourglassFull
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.kevalpatel2106.core.ui.component.ItemPropertyView
import com.kevalpatel2106.core.ui.component.BuildStatusView
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.feature.build.detail.model.BuildDetailViewState

@Composable
internal fun BuildPropertyInfoCard(viewState: BuildDetailViewState) = OutlinedCard(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = Spacing.SPACING_REGULAR),
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(Spacing.SPACING_SMALL),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.History,
                contentDescription = stringResource(id = R.string.build_detail_build_status_label),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = Spacing.SPACING_SMALL),
            )
            Text(
                text = stringResource(id = R.string.build_detail_build_status_label),
                modifier = Modifier.padding(end = Spacing.SPACING_SMALL),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
            )
            BuildStatusView(
                buildStatus = viewState.buildStatus,
                modifier = Modifier.padding(end = Spacing.SPACING_SMALL),
            )
            Text(
                text = viewState.buildStatusText,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
        ItemPropertyView(
            icon = Icons.Filled.AccountTree,
            label = R.string.build_detail_branch_label,
            value = viewState.headBranch,
            labelColor = MaterialTheme.colorScheme.primary,
            labelFontWeight = FontWeight.Bold,
        )
        ItemPropertyView(
            icon = Icons.Filled.AdsClick,
            label = R.string.build_detail_triggered_by_label,
            value = viewState.headBranch,
            labelColor = MaterialTheme.colorScheme.primary,
            labelFontWeight = FontWeight.Bold,
        )
        ItemPropertyView(
            icon = Icons.Filled.History,
            label = R.string.build_list_triggered_time_label,
            value = viewState.triggeredTime,
            labelColor = MaterialTheme.colorScheme.primary,
            labelFontWeight = FontWeight.Bold,
        )
        ItemPropertyView(
            icon = Icons.Filled.HourglassFull,
            label = R.string.build_list_execution_time_label,
            value = viewState.executionTime,
            labelColor = MaterialTheme.colorScheme.primary,
            labelFontWeight = FontWeight.Bold,
        )
    }
}

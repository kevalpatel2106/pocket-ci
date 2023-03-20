package com.kevalpatel2106.feature.build.detail.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_REGULAR
import com.kevalpatel2106.core.ui.resource.Spacing.SPACING_SMALL
import com.kevalpatel2106.core.ui.theme.colorOrange
import com.kevalpatel2106.feature.build.detail.BuildDetailViewModel

@Composable
internal fun BuildDetailScreen(
    viewModel: BuildDetailViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SPACING_REGULAR)
            .verticalScroll(scrollState),
    ) {
        BuildNumberWithWorkflowView(viewState.numberText, viewState.workflowName)
        BuildPropertyInfoCard(viewState)
        if (viewState.showCommitInfo) {
            BuildMessageCard(
                message = viewState.commitMessage.orEmpty(),
                showFullScreenButton = viewState.showCommitFullViewButton,
            ) { viewModel.onViewCommitMessageClicked() }
        }
        if (viewState.showAbortInfo) {
            BuildMessageCard(
                message = viewState.abortMessage.orEmpty(),
                showFullScreenButton = viewState.showAbortFullViewButton,
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.colorOrange),
            ) { viewModel.onViewAbortMessageClicked() }
        }
        BuildDetailNavigationButton(
            viewState = viewState,
            onOpenBuildLogClick = { viewModel.onOpenBuildLogs() },
            onOpenJobsClick = { viewModel.onOpenJobs() },
            onOpenArtifactsClick = { viewModel.onOpenArtifacts() },
        )
    }
}

@Composable
private fun BuildNumberWithWorkflowView(
    numberText: String,
    workflowName: String,
) = Row(
    verticalAlignment = Alignment.Bottom,
    modifier = Modifier.fillMaxWidth(),
) {
    Text(
        text = numberText,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        color = MaterialTheme.colorScheme.colorOrange,
        style = MaterialTheme.typography.titleLarge,
    )
    Text(
        text = workflowName,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        color = MaterialTheme.colorScheme.colorOrange,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = SPACING_SMALL),
    )
}
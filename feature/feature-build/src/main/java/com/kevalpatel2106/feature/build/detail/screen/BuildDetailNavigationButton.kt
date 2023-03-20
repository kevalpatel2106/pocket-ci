package com.kevalpatel2106.feature.build.detail.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.feature.build.detail.model.BuildDetailViewState

@Composable
internal fun BuildDetailNavigationButton(
    viewState: BuildDetailViewState,
    onOpenBuildLogClick: () -> Unit,
    onOpenJobsClick: () -> Unit,
    onOpenArtifactsClick: () -> Unit,
) {
    if (viewState.showBuildLogButton) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.SPACING_SMALL),
            onClick = onOpenBuildLogClick,
        ) {
            Text(text = stringResource(id = R.string.build_detail_open_logs))
        }
    }
    if (viewState.showJobsListButton) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.SPACING_SMALL),
            onClick = onOpenJobsClick,
        ) {
            Text(text = stringResource(id = R.string.build_detail_open_jobs))
        }
    }
    if (viewState.showArtifactsButton) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.SPACING_SMALL),
            onClick = onOpenArtifactsClick,
        ) {
            Text(text = stringResource(id = R.string.build_detail_open_artifacts))
        }
    }
}

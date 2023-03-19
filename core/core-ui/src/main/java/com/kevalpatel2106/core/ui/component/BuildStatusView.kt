package com.kevalpatel2106.core.ui.component

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kevalpatel2106.core.ui.resource.Spacing
import com.kevalpatel2106.core.ui.theme.PocketCITheme
import com.kevalpatel2106.core.ui.theme.colorGray
import com.kevalpatel2106.core.ui.theme.colorGreen
import com.kevalpatel2106.core.ui.theme.colorRed
import com.kevalpatel2106.core.ui.theme.colorYellow
import com.kevalpatel2106.core.ui.useCase.GetBuildStatusImage
import com.kevalpatel2106.entity.BuildStatus
import com.kevalpatel2106.entity.BuildStatus.ABORT
import com.kevalpatel2106.entity.BuildStatus.FAIL
import com.kevalpatel2106.entity.BuildStatus.PENDING
import com.kevalpatel2106.entity.BuildStatus.RUNNING
import com.kevalpatel2106.entity.BuildStatus.SKIPPED
import com.kevalpatel2106.entity.BuildStatus.SUCCESS
import com.kevalpatel2106.entity.BuildStatus.UNKNOWN
import java.util.concurrent.TimeUnit

private val ANIMATION_DURATION = TimeUnit.SECONDS.toMillis(1).toInt()
private const val MAX_ALPHA = 1f

@Composable
fun BuildStatusView(
    buildStatus: BuildStatus,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .size(Spacing.SPACING_LARGE)
            .aspectRatio(1f),
    ) {
        when (buildStatus) {
            PENDING, RUNNING -> RunningBuildStatus(buildStatus)
            UNKNOWN, SKIPPED, ABORT, SUCCESS, FAIL -> ConcludedBuildStatus(buildStatus)
        }
    }
}

@Composable
private fun ConcludedBuildStatus(buildStatus: BuildStatus) {
    val getBuildStatusImage = remember { GetBuildStatusImage() }
    val imageData = getBuildStatusImage(
        buildStatus = buildStatus,
        red = MaterialTheme.colorScheme.colorRed,
        yellow = MaterialTheme.colorScheme.colorYellow,
        green = MaterialTheme.colorScheme.colorGreen,
        gray = MaterialTheme.colorScheme.colorGray,
    )

    Icon(
        imageVector = imageData.icon,
        contentDescription = stringResource(id = imageData.contentDescription),
        tint = imageData.tint,
    )
}

@Composable
private fun BoxScope.RunningBuildStatus(buildStatus: BuildStatus) {
    val alphaAnimator by animateFloatAsState(
        targetValue = MAX_ALPHA,
        animationSpec = infiniteRepeatable(
            animation = tween(ANIMATION_DURATION, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.colorYellow,
            strokeWidth = 4.dp,
        )
        if (buildStatus == PENDING) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(Spacing.SPACING_SMALL)
                    .background(MaterialTheme.colorScheme.colorYellow, shape = CircleShape)
                    .alpha(alphaAnimator),
            )
        }
    }
}

@Composable
@Preview(
    name = "Normal",
    group = "Build Status View",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    group = "Build Status View",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
fun BuildStatusPreview() = PocketCITheme {
    Column {
        BuildStatus.values().forEach {
            Row(Modifier.fillMaxWidth()) {
                BuildStatusView(buildStatus = it)
                Text(it.name, modifier = Modifier.padding(start = Spacing.SPACING_REGULAR))
            }
            Spacer(modifier = Modifier.size(Spacing.SPACING_SMALL))
        }
    }
}
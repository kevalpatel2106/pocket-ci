package com.kevalpatel2106.coreViews

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.kevalpatel2106.coreViews.useCase.GetBuildStatusImage
import com.kevalpatel2106.coreViews.useCase.GetBuildStatusImageTint
import com.kevalpatel2106.entity.BuildStatus
import com.kevalpatel2106.entity.isInProgress
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
@AndroidEntryPoint
class BuildStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @Inject
    internal lateinit var getBuildStatusImage: GetBuildStatusImage

    @Inject
    internal lateinit var getBuildStatusImageTint: GetBuildStatusImageTint

    private val view = View.inflate(context, R.layout.view_build_status, this)

    var status: BuildStatus = BuildStatus.UNKNOWN
        set(value) {
            field = value
            onBuildStatusChanged()
        }

    init {
        onBuildStatusChanged()
        view.findViewById<View>(R.id.buildStatusRunningDot)
            .startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_build_status_dot))
    }

    private fun onBuildStatusChanged() = with(view) {
        findViewById<View>(R.id.buildStatusRunningLoader).isVisible =
            status == BuildStatus.RUNNING
        findViewById<View>(R.id.buildStatusRunningDot).isVisible = status.isInProgress()
        findViewById<ImageView>(R.id.buildStatusImageView).updateBuildStatusImage()
    }

    private fun ImageView.updateBuildStatusImage() {
        isVisible = !status.isInProgress()
        if (!isVisible) return

        setImageResource(getBuildStatusImage(status))
        setColorFilter(
            ContextCompat.getColor(context, getBuildStatusImageTint(status)),
            PorterDuff.Mode.SRC_IN
        )
    }

    companion object {

        @JvmStatic
        @BindingAdapter("app:buildStatus")
        fun setBuildStatus(view: View, status: BuildStatus) {
            (view as? BuildStatusView)?.status = status
        }
    }
}

package com.kevalpatel2106.coreViews.errorView

import androidx.annotation.IntDef
import com.kevalpatel2106.coreViews.errorView.ActionButtonMode.Companion.ACTION_CLOSE
import com.kevalpatel2106.coreViews.errorView.ActionButtonMode.Companion.ACTION_RETRY

@IntDef(ACTION_RETRY, ACTION_CLOSE)
internal annotation class ActionButtonMode {

    companion object {
        const val ACTION_RETRY = 0
        const val ACTION_CLOSE = 1
    }
}

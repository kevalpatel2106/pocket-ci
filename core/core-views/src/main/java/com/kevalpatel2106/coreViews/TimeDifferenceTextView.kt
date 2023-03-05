package com.kevalpatel2106.coreViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import androidx.databinding.BindingAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@Suppress("MemberVisibilityCanBePrivate")
@AndroidEntryPoint
class TimeDifferenceTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var dates: TimeDifferenceData? = null

    var textAppend: String? = null

    var showMorePrecise: Boolean = false

    init {
        setupAttrs(attrs)
    }

    private fun setupAttrs(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.TimeDifferenceTextView) {
            textAppend = getString(R.styleable.TimeDifferenceTextView_appendText)
            showMorePrecise = getBoolean(R.styleable.TimeDifferenceTextView_displayAccurate, false)
        }
    }

    data class TimeDifferenceData(
        val dateOfEventStart: Date,
        val dateOfEventEnd: Date?,
    )

    companion object {

        @JvmStatic
        @BindingAdapter("app:dates")
        fun setDateStart(view: View, timeDifferenceData: TimeDifferenceData) {
            (view as? TimeDifferenceTextView)?.dates = timeDifferenceData
        }
    }
}

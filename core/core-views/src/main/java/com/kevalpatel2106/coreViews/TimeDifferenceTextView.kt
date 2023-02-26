package com.kevalpatel2106.coreViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.kevalpatel2106.coreViews.useCase.LiveTimeDifferenceTicker
import com.kevalpatel2106.coreViews.useCase.TimeDifferenceFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
@AndroidEntryPoint
class TimeDifferenceTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private var tickJob: Job? = null

    @Inject
    internal lateinit var timeDifferenceFormatter: TimeDifferenceFormatter

    @Inject
    internal lateinit var liveTimeDifferenceTicker: LiveTimeDifferenceTicker

    var dates: TimeDifferenceData? = null
        set(value) {
            field = value
            updateTimeDifference()
        }

    private val showLiveTimeDifference: Boolean
        get() = dates?.dateOfEventEnd == null

    var textAppend: String? = null
        set(value) {
            field = value
            updateTimeDifference()
        }

    var showMorePrecise: Boolean = false
        set(value) {
            field = value
            updateTimeDifference()
        }

    init {
        setupAttrs(attrs)
    }

    private fun setupAttrs(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.TimeDifferenceTextView) {
            textAppend = getString(R.styleable.TimeDifferenceTextView_appendText)
            showMorePrecise = getBoolean(R.styleable.TimeDifferenceTextView_displayAccurate, false)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateTimeDifference()
    }

    private fun updateTimeDifference() {
        val dateStart = dates?.dateOfEventStart
        val dateEnd = dates?.dateOfEventEnd
        tickJob?.cancel()
        tickJob = null

        when {
            dateStart == null -> text = ""
            showLiveTimeDifference -> tickJob = showLiveTimeDifference(dateStart)
            dateEnd != null -> {
                text = timeDifferenceFormatter(dateStart, dateEnd, textAppend, showMorePrecise)
            }
            else -> error("End date is not set.")
        }
    }

    private fun showLiveTimeDifference(dateOfEventStart: Date) =
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            liveTimeDifferenceTicker(dateOfEventStart, showMorePrecise)
                .map { now ->
                    timeDifferenceFormatter(dateOfEventStart, now, textAppend, showMorePrecise)
                }.catch {
                    it.printStackTrace()
                }.collectLatest { timeFormatted ->
                    text = timeFormatted
                }
        }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        tickJob?.cancel()
        tickJob = null
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

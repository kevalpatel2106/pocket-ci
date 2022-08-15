package com.kevalpatel2106.coreViews.errorView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.coreViews.R
import com.kevalpatel2106.coreViews.databinding.ViewErrorStateBinding
import com.kevalpatel2106.coreViews.errorView.ActionButtonMode.Companion.ACTION_CLOSE
import com.kevalpatel2106.coreViews.errorView.ActionButtonMode.Companion.ACTION_RETRY
import com.kevalpatel2106.entity.DisplayError
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
@AndroidEntryPoint
class ErrorStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @Inject
    internal lateinit var displayErrorMapper: DisplayErrorMapper

    private val binding = ViewErrorStateBinding.inflate(LayoutInflater.from(context), this, true)

    var headlineText: String
        get() = binding.headlineTextView.text.toString()
        set(value) {
            binding.headlineTextView.isVisible = value.isNotBlank()
            binding.headlineTextView.text = value
        }

    private var error: DisplayError? = null
        set(value) {
            binding.debugInfoButton.isVisible = !value?.technicalMessage.isNullOrBlank()
            field = value
        }

    var descriptionText: String?
        get() = binding.descriptionTextView.text.toString()
        set(value) {
            binding.descriptionTextView.isVisible = !value.isNullOrBlank()
            binding.descriptionTextView.text = value
        }

    var showActionButtons: Boolean
        get() = binding.actionButton.isVisible
        set(value) {
            binding.actionButton.isVisible = value
        }

    var retryButtonClickListener: OnClickListener? = null

    var closeButtonClickListener: OnClickListener? = null

    @ActionButtonMode
    var actionButtonMode: Int = ACTION_RETRY
        set(value) {
            val titleRes = when (value) {
                ACTION_CLOSE -> R.string.error_view_close_button_title
                ACTION_RETRY -> R.string.error_view_retry_button_title
                else -> error("Invalid action button mode $value")
            }
            val clickListener = when (value) {
                ACTION_CLOSE -> closeButtonClickListener
                ACTION_RETRY -> retryButtonClickListener
                else -> error("Invalid action button mode $value")
            }
            binding.actionButton.setText(titleRes)
            binding.actionButton.setOnClickListener(clickListener)
            field = value
        }

    init {
        setupAttrs(attrs)
        error = null
        binding.debugInfoButton.setOnClickListener {
            error?.let { e -> DebugInfoAlertDialogBuilder(context).show(e) }
        }
    }

    private fun setupAttrs(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.ErrorStateView) {
            headlineText = getString(R.styleable.ErrorStateView_headlineText)
                ?: context.getString(R.string.error_unknown_title)
            descriptionText = getString(R.styleable.ErrorStateView_descriptionText)
                ?: context.getString(R.string.error_unknown_message)
            showActionButtons = getBoolean(R.styleable.ErrorStateView_showCallToAction, true)
        }
    }

    fun setErrorThrowable(throwable: Throwable) {
        val displayError = displayErrorMapper(throwable)
        headlineText = displayError.headline
        descriptionText = displayError.message
        error = displayError
        actionButtonMode = if (displayError.nonRecoverable) ACTION_CLOSE else ACTION_RETRY
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:onRetryButtonClick")
        fun setRetryButtonClickListener(view: View, click: OnClickListener) {
            (view as? ErrorStateView)?.retryButtonClickListener = click
        }

        @JvmStatic
        @BindingAdapter("app:onCloseButtonClick")
        fun setCloseButtonClickListener(view: View, click: OnClickListener) {
            (view as? ErrorStateView)?.closeButtonClickListener = click
        }
    }
}

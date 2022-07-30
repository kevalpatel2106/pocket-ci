package com.kevalpatel2106.coreViews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.ID_NULL
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@Suppress("MemberVisibilityCanBePrivate")
class ErrorStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = View.inflate(context, R.layout.view_error_state, this)

    var headlineText: String
        get() = binding.findViewById<TextView>(R.id.headlineTextView).text.toString()
        set(value) {
            binding.findViewById<TextView>(R.id.headlineTextView).text = value
        }

    var descriptionText: String?
        get() = binding.findViewById<TextView>(R.id.descriptionTextView).text.toString()
        set(value) {
            binding.findViewById<TextView>(R.id.descriptionTextView).isVisible =
                !value.isNullOrBlank()
            binding.findViewById<TextView>(R.id.descriptionTextView).text = value
        }

    var retryActionText: String
        get() = binding.findViewById<Button>(R.id.retryActionButton).text.toString()
        set(value) {
            binding.findViewById<Button>(R.id.retryActionButton).text = value
        }

    var showRetryButton: Boolean
        get() = binding.findViewById<Button>(R.id.retryActionButton).isVisible
        set(value) {
            binding.findViewById<Button>(R.id.retryActionButton).isVisible = value
        }

    var illustration: Drawable?
        get() = binding.findViewById<ImageView>(R.id.illustrationImageView).drawable
        set(value) {
            if (value != null) {
                binding.findViewById<ImageView>(R.id.illustrationImageView).isVisible = true
                binding.findViewById<ImageView>(R.id.illustrationImageView).setImageDrawable(value)
            } else {
                binding.findViewById<ImageView>(R.id.illustrationImageView).isVisible = false
            }
        }

    var retryButtonClickListener: OnClickListener
        get() {
            error("Cannot get click listener")
        }
        set(value) {
            binding.findViewById<Button>(R.id.retryActionButton).setOnClickListener(value)
        }

    init {
        setupAttrs(attrs)
    }

    private fun setupAttrs(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.ErrorStateView) {
            headlineText = getString(R.styleable.ErrorStateView_headlineText)
                ?: context.getString(R.string.standard_error_message_title)
            descriptionText = getString(R.styleable.ErrorStateView_descriptionText)
                ?: context.getString(R.string.standard_error_message_subtitle)

            showRetryButton = getBoolean(R.styleable.ErrorStateView_showCallToAction, true)
            retryActionText = getString(R.styleable.ErrorStateView_callToActionText)
                ?: context.getString(R.string.standard_retry_button_title)

            val drawableId = getResourceId(R.styleable.ErrorStateView_image, ID_NULL)
            illustration = if (drawableId != ID_NULL) {
                ContextCompat.getDrawable(context, drawableId)
            } else null
        }
    }

    fun setErrorThrowable(throwable: Throwable) {
        descriptionText = throwable.message.orEmpty()
    }

    fun setIllustration(@DrawableRes imageResource: Int) {
        illustration = ContextCompat.getDrawable(context, imageResource)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("onRetryButtonClick")
        fun setRetryButtonClickListener(view: View, click: () -> Unit) {
            (view as? ErrorStateView)?.retryButtonClickListener = OnClickListener { click.invoke() }
        }
    }
}

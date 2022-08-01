@file:Suppress("unused")

package com.kevalpatel2106.core.databinding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputLayout
import com.kevalpatel2106.core.R
import com.kevalpatel2106.entity.Url

@BindingAdapter("app:displayChild")
fun displayChild(view: View, pos: Int) {
    (view as? ViewFlipper)?.displayedChild = pos
}

@BindingAdapter("app:onEditorAction")
fun onEditorActionListener(view: View, block: Runnable) {
    (view as? EditText)?.setOnEditorActionListener { _, _, _ ->
        block.run()
        return@setOnEditorActionListener false
    }
}

@BindingAdapter("android:errorText")
fun enableView(view: View, @StringRes res: Int?) {
    (view as? TextInputLayout)?.error = if (res != null) view.context.getString(res) else null
}

@BindingAdapter("android:contentDescription")
fun contentDescription(view: View, @StringRes res: Int) {
    (view as? ImageView)?.contentDescription = view.context.getString(res)
}

@BindingAdapter("android:src")
fun setImageSrc(view: View, @DrawableRes res: Int) {
    (view as? ImageView)?.setImageResource(res)
}

@BindingAdapter("imageUrl", "placeholder", "error", requireAll = false)
fun setImageUrl(
    imgView: ImageView,
    imgUrl: Url?,
    placeholder: Drawable?,
    error: Drawable?,
) {
    val options = RequestOptions()
        .placeholder(
            placeholder ?: imgView.context.getDrawable(R.drawable.ic_account_place_holder)
        )
        .error(error ?: imgView.context.getDrawable(R.drawable.ic_account_place_holder))
    Glide.with(imgView.context).load(imgUrl?.value).apply(options).into(imgView)
}

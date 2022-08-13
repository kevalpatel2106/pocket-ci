package com.kevalpatel2106.feature.build.list.adapter

import androidx.recyclerview.widget.DiffUtil

internal object BuildDiffCallback : DiffUtil.ItemCallback<BuildListItem>() {
    override fun areContentsTheSame(oldItem: BuildListItem, newItem: BuildListItem) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: BuildListItem, newItem: BuildListItem) =
        oldItem.compareId == newItem.compareId
}

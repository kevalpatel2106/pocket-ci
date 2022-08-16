package com.kevalpatel2106.feature.artifacts.list.adapter

import androidx.recyclerview.widget.DiffUtil

internal object ArtifactListItemDiffCallback : DiffUtil.ItemCallback<ArtifactListItem>() {
    override fun areContentsTheSame(oldItem: ArtifactListItem, newItem: ArtifactListItem) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: ArtifactListItem, newItem: ArtifactListItem) =
        oldItem.compareId == newItem.compareId
}

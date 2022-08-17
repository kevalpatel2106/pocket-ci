package com.kevalpatel2106.feature.project.list.adapter

import androidx.recyclerview.widget.DiffUtil

internal object ProjectListDiffCallback : DiffUtil.ItemCallback<ProjectListItem>() {
    override fun areContentsTheSame(oldItem: ProjectListItem, newItem: ProjectListItem) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: ProjectListItem, newItem: ProjectListItem) =
        oldItem.compareId == newItem.compareId
}

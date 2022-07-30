package com.kevalpatel2106.projects.list.adapter

import androidx.recyclerview.widget.DiffUtil

internal object ProjectDiffCallback : DiffUtil.ItemCallback<ProjectListItem>() {
    override fun areContentsTheSame(oldItem: ProjectListItem, newItem: ProjectListItem) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: ProjectListItem, newItem: ProjectListItem) =
        oldItem.compareId == newItem.compareId
}

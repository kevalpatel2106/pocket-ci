package com.kevalpatel2106.feature.jobs.list.adapter

import androidx.recyclerview.widget.DiffUtil

internal object JobDiffCallback : DiffUtil.ItemCallback<JobListItem>() {
    override fun areContentsTheSame(oldItem: JobListItem, newItem: JobListItem) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: JobListItem, newItem: JobListItem) =
        oldItem.compareId == newItem.compareId
}

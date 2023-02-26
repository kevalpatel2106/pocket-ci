package com.kevalpatel2106.feature.job.list.adapter

import androidx.recyclerview.widget.DiffUtil

internal object JobListDiffCallback : DiffUtil.ItemCallback<JobListItem>() {
    override fun areContentsTheSame(oldItem: JobListItem, newItem: JobListItem) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: JobListItem, newItem: JobListItem) =
        oldItem.compareId == newItem.compareId
}

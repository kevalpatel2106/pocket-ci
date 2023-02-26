package com.kevalpatel2106.feature.job.list.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.feature.job.list.adapter.JobListItem.JobItem
import com.kevalpatel2106.feature.job.list.adapter.JobListItemType.JOB_ITEM

internal class JobsListAdapter(
    private val callback: JobsAdapterCallback,
) : PagingDataAdapter<JobListItem, RecyclerView.ViewHolder>(JobListDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        JOB_ITEM.ordinal -> JobViewHolder.create(parent, callback)
        else -> error("Invalid view holder type: $viewType")
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.listItemType?.ordinal
            ?: error("Invalid position: $position. Current list size is $itemCount.")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when (holder) {
                is JobViewHolder -> holder.bind(it as JobItem)
                else -> error("Invalid view holder: $holder")
            }
        }
    }
}

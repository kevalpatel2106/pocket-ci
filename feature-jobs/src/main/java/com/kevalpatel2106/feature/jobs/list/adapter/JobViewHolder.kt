package com.kevalpatel2106.feature.jobs.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.feature.jobs.databinding.ListItemJobBinding
import com.kevalpatel2106.feature.jobs.list.adapter.JobListItem.JobItem

internal class JobViewHolder(
    private val binding: ListItemJobBinding,
    private val adapterCallback: JobsAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listItem: JobItem) = with(binding) {
        jobItem = listItem
        callback = adapterCallback
    }

    companion object {
        fun create(parent: ViewGroup, callback: JobsAdapterCallback): JobViewHolder {
            val binding = ListItemJobBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return JobViewHolder(binding, callback)
        }
    }
}

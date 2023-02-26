package com.kevalpatel2106.feature.project.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.feature.project.databinding.ListItemProjectBinding
import com.kevalpatel2106.feature.project.list.adapter.ProjectListItem.ProjectItem

internal class ProjectViewHolder(
    private val binding: ListItemProjectBinding,
    private val adapterCallback: ProjectListAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listItem: ProjectItem) = with(binding) {
        project = listItem.project
        callback = adapterCallback
    }

    companion object {
        fun create(parent: ViewGroup, callback: ProjectListAdapterCallback): ProjectViewHolder {
            val binding = ListItemProjectBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ProjectViewHolder(binding, callback)
        }
    }
}

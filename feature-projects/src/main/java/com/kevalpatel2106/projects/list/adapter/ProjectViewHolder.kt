package com.kevalpatel2106.projects.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.projects.databinding.ListItemProjectBinding
import com.kevalpatel2106.projects.list.adapter.ProjectListItem.ProjectItem

internal class ProjectViewHolder(
    private val binding: ListItemProjectBinding,
    private val adapterCallback: ProjectAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listItem: ProjectItem) = with(binding) {
        project = listItem.project
        callback = adapterCallback
    }

    companion object {
        fun create(parent: ViewGroup, callback: ProjectAdapterCallback): ProjectViewHolder {
            val binding = ListItemProjectBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ProjectViewHolder(binding, callback)
        }
    }
}

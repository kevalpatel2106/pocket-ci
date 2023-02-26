package com.kevalpatel2106.feature.project.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.feature.project.databinding.ListItemProjectHeaderBinding
import com.kevalpatel2106.feature.project.list.adapter.ProjectListItem.HeaderItem

internal class ProjectHeaderViewHolder(
    private val binding: ListItemProjectHeaderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(headerItem: HeaderItem) {
        binding.item = headerItem
    }

    companion object {
        fun create(parent: ViewGroup): ProjectHeaderViewHolder {
            val binding = ListItemProjectHeaderBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ProjectHeaderViewHolder(binding)
        }
    }
}

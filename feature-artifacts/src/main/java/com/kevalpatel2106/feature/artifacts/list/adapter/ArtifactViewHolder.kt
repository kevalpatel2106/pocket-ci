package com.kevalpatel2106.feature.artifacts.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.feature.artifacts.databinding.ListItemArtifactBinding
import com.kevalpatel2106.feature.artifacts.list.adapter.ArtifactListItem.ArtifactItem

internal class ArtifactViewHolder(
    private val binding: ListItemArtifactBinding,
    private val adapterCallback: ArtifactAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listItem: ArtifactItem) = with(binding) {
        artifactItem = listItem
        callback = adapterCallback
    }

    companion object {
        fun create(parent: ViewGroup, callback: ArtifactAdapterCallback): ArtifactViewHolder {
            val binding = ListItemArtifactBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ArtifactViewHolder(binding, callback)
        }
    }
}

package com.kevalpatel2106.registration.ciSelection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.registration.databinding.ListItemCiSelectionInfoBinding

internal class CISelectionViewHolder(
    private val binding: ListItemCiSelectionInfoBinding,
    private val callback: CISelectionAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ciInfo: CIInfo) {
        binding.ciInfo = ciInfo
        binding.callback = callback
    }

    companion object {
        fun create(parent: ViewGroup, callback: CISelectionAdapterCallback): CISelectionViewHolder {
            val binding = ListItemCiSelectionInfoBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return CISelectionViewHolder(binding, callback)
        }
    }
}

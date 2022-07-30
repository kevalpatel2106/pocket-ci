package com.kevalpatel2106.registration.ciSelection.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kevalpatel2106.entity.CIInfo

internal object CiSelectionDiffCallback : DiffUtil.ItemCallback<CIInfo>() {
    override fun areContentsTheSame(oldItem: CIInfo, newItem: CIInfo) = oldItem == newItem
    override fun areItemsTheSame(oldItem: CIInfo, newItem: CIInfo) =
        oldItem.name == newItem.name && oldItem.type == newItem.type
}

package com.kevalpatel2106.accounts.list.adapter

import androidx.recyclerview.widget.DiffUtil

internal object AccountsDiffCallback : DiffUtil.ItemCallback<AccountsListItem>() {
    override fun areContentsTheSame(oldItem: AccountsListItem, newItem: AccountsListItem): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: AccountsListItem, newItem: AccountsListItem): Boolean =
        oldItem.compareId == newItem.compareId
}

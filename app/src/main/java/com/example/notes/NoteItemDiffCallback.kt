package com.example.notes

import androidx.recyclerview.widget.DiffUtil

class NoteItemDiffCallback : DiffUtil.ItemCallback<NoteItem>() {

    override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem) = oldItem == newItem
}
package com.example.notes.ui.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.example.notes.entities.NoteItem

class NoteItemDiffCallback : DiffUtil.ItemCallback<NoteItem>() {

    override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem) = oldItem == newItem
}
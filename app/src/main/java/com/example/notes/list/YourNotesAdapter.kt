package com.example.notes.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.entities.NoteItem
import com.example.notes.callbacks.NoteItemDiffCallback
import com.example.notes.R
import com.example.notes.databinding.NoteItemBinding

class YourNotesAdapter :
    ListAdapter<NoteItem, YourNotesAdapter.NoteHolder>(NoteItemDiffCallback()) {

    var onClick: ((noteItemId: Long) -> Unit)? = null

    inner class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = NoteItemBinding.bind(item)

        fun bind(note: NoteItem) = with(binding) {
            noteItem = note

            cardViewNote.setOnClickListener {
                onClick?.invoke(note.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
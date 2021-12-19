package com.example.notes.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.NoteItem
import com.example.notes.NoteItemDiffCallback
import com.example.notes.R
import com.example.notes.databinding.NoteItemBinding

class YourNotesAdapter(private var onClick: (note: NoteItem) -> Unit) :
    ListAdapter<NoteItem, YourNotesAdapter.NoteHolder>(NoteItemDiffCallback()) {

    inner class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = NoteItemBinding.bind(item)

        fun bind(note: NoteItem) = with(binding) {
            textTitle.text = note.title
            textText.text = note.text
            textData.text = note.dateOfCreation

            cardViewNote.setOnClickListener {
                onClick(note)
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
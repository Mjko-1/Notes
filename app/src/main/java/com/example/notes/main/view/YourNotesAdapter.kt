package com.example.notes.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Constant
import com.example.notes.NoteItem
import com.example.notes.R
import com.example.notes.databinding.NoteItemBinding

class YourNotesAdapter(
    private val values: List<NoteItem>,
    private val activity: MainActivity
) :
    RecyclerView.Adapter<YourNotesAdapter.NoteHolder>() {

    inner class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = NoteItemBinding.bind(item)

        fun bind(note: NoteItem) = with(binding) {
            textTitle.text = note.title
            textText.text = note.text
            textData.text = note.dateOfCreation

            cardViewNote.setOnClickListener {
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                val bundle = Bundle().apply {
                    putSerializable(Constant.NOTE_TAG, note)
                }

                val fragmentToManager = NoteDescriptionFragment.newInstance()
                fragmentToManager.arguments = bundle

                activity.openFragment(R.id.fragment_container, fragmentToManager, true)
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
        holder.bind(values[position])
    }

    override fun getItemCount() = values.size
}
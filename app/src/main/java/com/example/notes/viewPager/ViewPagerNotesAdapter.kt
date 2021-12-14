package com.example.notes.viewPager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.NoteItem
import com.example.notes.databinding.FragmentNoteDescriptionBinding

class ViewPagerAdapter(private var values: List<NoteItem>) :
    RecyclerView.Adapter<ViewPagerAdapter.PageHolder>() {

    inner class PageHolder(private var binding: FragmentNoteDescriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteItem) = with(binding) {
            editTitle.setText(note.title)
            editText.setText(note.text)
            textDate.text = note.dateOfCreation
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder =
        PageHolder(
            FragmentNoteDescriptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size
}
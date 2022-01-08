package com.example.notes.viewPager

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notes.entities.NoteItem
import com.example.notes.note.NoteDescriptionFragment

class ViewPagerAdapter(fragmentActivity: AppCompatActivity) :
    FragmentStateAdapter(fragmentActivity) {

    var values: List<NoteItem> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(values: List<NoteItem>) {
        this.values = values
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size

    override fun createFragment(position: Int): Fragment =
        NoteDescriptionFragment.newInstanceEditNote(values[position].id)
}
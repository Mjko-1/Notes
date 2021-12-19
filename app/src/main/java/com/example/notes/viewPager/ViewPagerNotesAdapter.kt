package com.example.notes.viewPager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notes.NoteItem
import com.example.notes.note.NoteDescriptionFragment

class ViewPagerAdapter(fragmentActivity: AppCompatActivity, private var values: List<NoteItem>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = values.size

    override fun createFragment(position: Int): Fragment =
        NoteDescriptionFragment.newInstance(values[position])
}
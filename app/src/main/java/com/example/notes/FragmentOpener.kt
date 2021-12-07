package com.example.notes

import androidx.fragment.app.Fragment

interface FragmentOpener {

    fun openFragment(resId: Int, classFragment: Fragment, onStack: Boolean)
}
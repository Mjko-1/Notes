package com.example.notes.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.example.notes.databinding.FragmentCustomViewBinding

class CustomViewFragment : Fragment() {

    private var _binding: FragmentCustomViewBinding? = null
    private val binding: FragmentCustomViewBinding
        get() = _binding ?: throw RuntimeException("FragmentCustomViewBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCustomViewBinding.inflate(layoutInflater).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAnimation.setOnClickListener {
            binding.htmlText.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.text_animation
                )
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        @JvmStatic
        fun newInstance() = CustomViewFragment()
    }
}
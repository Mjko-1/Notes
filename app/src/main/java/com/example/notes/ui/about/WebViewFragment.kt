package com.example.notes.ui.about

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.notes.conventions.ActionWithWebViewFragment
import com.example.notes.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment(), ActionWithWebViewFragment {

    private var _binding: FragmentWebViewBinding? = null
    private val binding: FragmentWebViewBinding
        get() = _binding ?: throw RuntimeException("FragmentWebViewBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWebViewBinding.inflate(layoutInflater).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView()

        val callbackList = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                with(binding) {
                    if (webView.canGoBack()) webView.goBack()
                    else requireActivity().onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callbackList)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun goBackStatus(): Boolean = binding.webView.canGoBack()

    override fun goBackPage() {
        binding.webView.goBack()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            loadUrl("https://github.com/evdokimovvladislav/Notes")
            settings.javaScriptEnabled = true
        }
        binding.webView.webViewClient = WebViewClient()
    }

    companion object {

        @JvmStatic
        fun newInstance() = WebViewFragment()
    }
}
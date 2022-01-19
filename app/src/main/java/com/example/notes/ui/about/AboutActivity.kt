package com.example.notes.ui.about

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAboutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupWebView()
        initToolbar()
    }

    override fun onBackPressed() = with(binding) {
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            loadUrl("https://github.com/evdokimovvladislav/Notes")
            settings.javaScriptEnabled = true
        }
        binding.webView.webViewClient = WebViewClient()
    }

    private fun initToolbar() = with(binding) {
        setSupportActionBar(aboutActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        aboutActivityToolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
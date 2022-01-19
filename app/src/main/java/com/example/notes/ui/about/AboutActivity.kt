package com.example.notes.ui.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.example.notes.conventions.ActionWithWebViewFragment
import com.example.notes.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAboutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initToolbar()
        initNavigation()

        if (savedInstanceState == null)
            openFragment(binding.fragmentContainer.id, WebViewFragment.newInstance())
    }

    override fun onBackPressed() {
        val requireFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (requireFragment is ActionWithWebViewFragment) {
            if (requireFragment.goBackStatus()) requireFragment.goBackPage()
            else finish()
        } else finish()
    }

    private fun initToolbar() = with(binding) {
        setSupportActionBar(aboutActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        aboutActivityToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.buttonWebView -> {
                    openFragment(binding.fragmentContainer.id, WebViewFragment.newInstance())
                    true
                }
                R.id.buttonLocation -> {
                    openFragment(binding.fragmentContainer.id, LocationFragment.newInstance())
                    true
                }
                R.id.buttonCustomView -> {
                    openFragment(binding.fragmentContainer.id, CustomViewFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun openFragment(resId: Int, classFragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(resId, classFragment)
            commit()
        }
    }
}
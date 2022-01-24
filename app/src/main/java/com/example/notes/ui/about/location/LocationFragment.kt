package com.example.notes.ui.about.location

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notes.BuildConfig
import com.example.notes.R
import com.example.notes.databinding.FragmentLocationBinding
import com.example.notes.ui.dialogs.LocationAgreePermissionDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    private val binding: FragmentLocationBinding
        get() = _binding ?: throw RuntimeException("FragmentLocationBinding == null")

    private val viewModel by lazy { ViewModelProvider(this)[LocationViewModel::class.java] }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val requestLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        ::onGotLocationPermissionResult
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLocationBinding.inflate(layoutInflater).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        observeViewModel()
        setupOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) binding.buttonSettings.isVisible = false
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun onGotLocationPermissionResult(grantResults: Map<String, Boolean>) {
        if (grantResults.entries.all { it.value }) {
            viewModel.requestLocationCoordinates(fusedLocationProviderClient)
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                binding.buttonSettings.isVisible = true
                showToast(getString(R.string.permission_denied))
            } else {
                LocationAgreePermissionDialog().show(parentFragmentManager, "Agree_dialog")
            }
        }
    }

    private fun setupOnClickListeners() {
        binding.buttonLocation.setOnClickListener {
            requestLocationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
        binding.buttonSettings.setOnClickListener {
            startActivity(
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                )
            )
        }
    }

    private fun observeViewModel() {
        viewModel.coordinatesToToast.observe(this) { coordinates ->
            showToast(coordinates)
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun checkPermissions(): Boolean = ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    companion object {

        @JvmStatic
        fun newInstance() = LocationFragment()
    }
}
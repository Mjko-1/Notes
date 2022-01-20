package com.example.notes.ui.about

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.example.notes.databinding.FragmentLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    private val binding: FragmentLocationBinding
        get() = _binding ?: throw RuntimeException("FragmentLocationBinding == null")

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLocationBinding.inflate(layoutInflater).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.buttonLocation.setOnClickListener {
            fetchLocation()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
            Toast.makeText(
                requireContext(),
                getString(R.string.permission_required),
                Toast.LENGTH_LONG
            ).show()
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                Toast.makeText(
                    requireContext(),
                    "${it.latitude}\n${it.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = LocationFragment()
    }
}
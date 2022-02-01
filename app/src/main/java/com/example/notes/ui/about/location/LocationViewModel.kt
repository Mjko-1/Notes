package com.example.notes.ui.about.location

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient

class LocationViewModel : ViewModel() {

    private val _coordinatesToToast = MutableLiveData<String>()
    val coordinatesToToast: LiveData<String>
        get() = _coordinatesToToast

    @SuppressLint("MissingPermission")
    fun requestLocationCoordinates(fusedLocationProviderClient: FusedLocationProviderClient) {
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener {
            if (it != null) {
                setCoordinatesText(latitude = it.latitude, longitude = it.longitude)
            }
        }
    }

    private fun setCoordinatesText(latitude: Double, longitude: Double) {
        _coordinatesToToast.value = "$latitude\n$longitude"
    }
}
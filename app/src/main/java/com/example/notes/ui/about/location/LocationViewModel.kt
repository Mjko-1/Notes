package com.example.notes.ui.about.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel: ViewModel() {

    private val _coordinatesToToast = MutableLiveData<String>()
    val coordinatesToToast: LiveData<String>
        get() = _coordinatesToToast

    fun setCoordinatesText (latitude: Double, longitude: Double){
        _coordinatesToToast.value = "$latitude\n$longitude"
    }
}
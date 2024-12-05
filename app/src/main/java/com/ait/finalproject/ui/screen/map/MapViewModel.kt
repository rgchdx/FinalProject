package com.ait.finalproject.ui.screen.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ait.finalproject.ui.navigation.MainNavigation
import com.google.android.gms.maps.model.LatLng

class MapViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    // todo: potentially set it to users curr location
    var location: LatLng by mutableStateOf(LatLng(47.497913, 19.0402))
    init {
        var lat = 47.4979
        var lng = 19.0402
        savedStateHandle.get<Float>(MainNavigation.ARG_LAT)?.let {
            lat = it.toDouble()
        }
        savedStateHandle.get<Float>(MainNavigation.ARG_LNG)?.let {
            lng = it.toDouble()
        }
        location = LatLng(lat, lng)
    }
}
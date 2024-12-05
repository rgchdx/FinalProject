package com.ait.finalproject.ui.screen.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MapViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    // todo: potentially set it to users curr location
    val location: LatLng = savedStateHandle.get<LatLng>("") ?: LatLng(47.497913, 19.0402)
}
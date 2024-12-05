package com.ait.finalproject.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    var selectedItem by mutableIntStateOf(0)

    val screens = listOf(
        MainNavigation.DiscoverScreen,
        MainNavigation.CreatePostScreen,
        MainNavigation.MapScreen
    )
}
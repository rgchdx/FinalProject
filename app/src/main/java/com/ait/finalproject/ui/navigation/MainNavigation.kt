package com.ait.finalproject.ui.navigation

sealed class MainNavigation(val route: String) {
    object CreatePostScreen : MainNavigation("createpost")
    object DiscoverScreen : MainNavigation("discover")
    object MapScreen : MainNavigation("map")
}
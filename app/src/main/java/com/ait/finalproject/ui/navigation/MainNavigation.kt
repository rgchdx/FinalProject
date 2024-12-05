package com.ait.finalproject.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.ait.finalproject.ui.screen.createpost.CreatePostScreen
import com.ait.finalproject.ui.screen.discover.DiscoverScreen
import com.ait.finalproject.ui.screen.map.MapScreen

sealed class MainNavigation(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val composable: @Composable ((NavHostController) -> Unit),
    val arguments: List<NamedNavArgument> = emptyList()
) {
    companion object {
        const val ARG_LAT = "lat"
        const val ARG_LNG = "lng"
    }

    object CreatePostScreen : MainNavigation(
        "createpost",
        Icons.Filled.Add,
        Icons.Outlined.Add,
        { CreatePostScreen() }
    )
    object DiscoverScreen : MainNavigation(
        "discover",
        Icons.Filled.Search,
        Icons.Outlined.Search,
        { DiscoverScreen(navigation = it) }
    )
    object MapScreen : MainNavigation(
        "map?$ARG_LAT={$ARG_LAT}&$ARG_LNG={$ARG_LNG}",
        Icons.Filled.Place,
        Icons.Outlined.Place,
        { MapScreen() },
        listOf(navArgument(ARG_LAT) {
            defaultValue = 47.497913
            type = NavType.FloatType
        }, navArgument(ARG_LNG) {
            defaultValue = 19.0402
            type = NavType.FloatType
        })
    ) {
        fun createRoute(lat: Double, lng: Double) = "map?$ARG_LAT=$lat&$ARG_LNG=$lng"
    }
}
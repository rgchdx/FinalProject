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
import androidx.navigation.NavHostController
import com.ait.finalproject.ui.screen.createpost.CreatePostScreen
import com.ait.finalproject.ui.screen.discover.DiscoverScreen
import com.ait.finalproject.ui.screen.map.MapScreen

sealed class MainNavigation(val route: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector, val composable: @Composable (navController: NavHostController) -> Unit) {
    object CreatePostScreen : MainNavigation("createpost", Icons.Filled.Add, Icons.Outlined.Add, { CreatePostScreen() })
    object DiscoverScreen : MainNavigation("discover", Icons.Filled.Search, Icons.Outlined.Search, { DiscoverScreen(navigation = it) })
    object MapScreen : MainNavigation("map", Icons.Filled.Place, Icons.Outlined.Place, { MapScreen() })
}
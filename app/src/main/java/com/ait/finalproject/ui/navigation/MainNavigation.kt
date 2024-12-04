package com.ait.finalproject.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainNavigation(val route: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector) {
    object CreatePostScreen : MainNavigation("createpost", Icons.Filled.Add, Icons.Outlined.Add)
    object DiscoverScreen : MainNavigation("discover", Icons.Filled.Search, Icons.Outlined.Search)
    object MapScreen : MainNavigation("map", Icons.Filled.Place, Icons.Outlined.Place)
}
package com.ait.finalproject.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

@Composable
fun MainNavigationBar(
    navController: NavHostController
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    val items = listOf(
        MainNavigation.DiscoverScreen,
        MainNavigation.CreatePostScreen,
        MainNavigation.MapScreen
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) item.selectedIcon else item.unselectedIcon,
                        contentDescription = null
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    navController.navigate(item.route)
                    selectedItem = index
                }
            )
        }
    }
}
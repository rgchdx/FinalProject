package com.ait.finalproject.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun MainNavigationBar(
    navController: NavHostController,
    viewModel: NavigationViewModel = viewModel()
) {

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
                        if (viewModel.selectedItem == index) item.selectedIcon else item.unselectedIcon,
                        contentDescription = null
                    )
                },
                selected = viewModel.selectedItem == index,
                onClick = {
                    navController.navigate(item.route)
                    viewModel.selectedItem = index
                }
            )
        }
    }
}
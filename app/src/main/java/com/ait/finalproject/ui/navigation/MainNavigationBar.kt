package com.ait.finalproject.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun MainNavigationBar(
    navController: NavHostController,
    viewModel: NavigationViewModel = viewModel()
) {
    NavigationBar(modifier = Modifier.background(Color(0xFFCDFFDE))) {
        viewModel.screens.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (viewModel.selectedItem == index) screen.selectedIcon else screen.unselectedIcon,
                        contentDescription = null
                    )
                },
                selected = viewModel.selectedItem == index,
                onClick = {
                    navController.navigate(screen.route)
                },

            )
        }
    }
}
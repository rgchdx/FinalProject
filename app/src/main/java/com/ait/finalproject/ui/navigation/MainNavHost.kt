package com.ait.finalproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ait.finalproject.ui.screen.createpost.CreatePostScreen
import com.ait.finalproject.ui.screen.discover.DiscoverScreen
import com.ait.finalproject.ui.screen.map.MapScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = MainNavigation.DiscoverScreen.route,
    viewModel: NavigationViewModel = viewModel()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        viewModel.screens.forEachIndexed { index, screen ->
            composable(screen.route) {  }
        }
        composable(MainNavigation.CreatePostScreen.route){ CreatePostScreen() }
        composable(MainNavigation.DiscoverScreen.route){ DiscoverScreen() }
        composable(MainNavigation.MapScreen.route){ MapScreen() }
    }
}
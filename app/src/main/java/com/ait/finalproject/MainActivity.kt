package com.ait.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ait.finalproject.ui.navigation.MainNavHost
import com.ait.finalproject.ui.navigation.MainNavigationBar
import com.ait.finalproject.ui.theme.FinalProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinalProjectTheme {
                val navHostController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        MainNavigationBar(navHostController)
                    }
                ) { innerPadding ->
                    MainNavHost(modifier = Modifier.padding(innerPadding), navController = navHostController)
                }
            }
        }
    }
}
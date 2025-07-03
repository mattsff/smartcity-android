package com.smartcity.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smartcity.presentation.screen.smartcity.SmartCityScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "smartcity") {
        composable("smartcity") { SmartCityScreen() }
    }
}


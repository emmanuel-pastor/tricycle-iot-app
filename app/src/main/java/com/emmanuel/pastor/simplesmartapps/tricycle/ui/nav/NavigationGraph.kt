package com.emmanuel.pastor.simplesmartapps.tricycle.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.screens.home.HomeScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    val homeRoute = BottomNavItem.Home.route
    NavHost(navController, startDestination = homeRoute) {
        composable(homeRoute) {
            HomeScreen()
        }
    }
}
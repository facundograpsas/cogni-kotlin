package com.example.kotlin.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kotlin.enums.Screen
import com.example.kotlin.ui.home.HomeScreen
import com.example.kotlin.ui.learn.LearnScreen

@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Learn.route) { LearnScreen() }
        composable(Screen.Stats.route) { HomeScreen() }
        composable(Screen.Profile.route) { HomeScreen() }
    }
}
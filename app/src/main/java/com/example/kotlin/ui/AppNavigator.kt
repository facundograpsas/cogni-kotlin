package com.example.kotlin.ui

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kotlin.enums.NavBarScreen
import com.example.kotlin.enums.OtherScreen
import com.example.kotlin.enums.RouteKeys
import com.example.kotlin.ui.countdown.CountdownScreen
import com.example.kotlin.ui.fallacy.FallacyIdentificationGame
import com.example.kotlin.ui.home.HomeScreen
import com.example.kotlin.ui.learn.ExercisesScreen
import com.example.kotlin.ui.learn.LearnScreen
import com.example.kotlin.viewmodels.NavigationViewModel
import kotlinx.coroutines.delay


@Composable
fun AppNavigator(navController: NavHostController, showNavBar : MutableState<Boolean>) {
    val navViewModel: NavigationViewModel = hiltViewModel()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        navViewModel.updateCurrentRoute(currentRoute ?: "")
    }

    NavHost(navController, startDestination = NavBarScreen.Home.route) {

        composable(NavBarScreen.Home.route) {
            HomeScreen(onNavigateToExercisesScreen = {navController.navigate(OtherScreen.Exercises.route)} ) }
        composable(NavBarScreen.Learn.route) { LearnScreen() }

        composable(OtherScreen.Exercises.route) {
            ExercisesScreen(onNavigateToFallacyScreen = {
                navigateToCountdown(navController, OtherScreen.Fallacy.route)
            })
        }
        composable(OtherScreen.Fallacy.route) {
            FallacyIdentificationGame()
        }

        composable(OtherScreen.Countdown.route) {
            val nextScreen = currentBackStackEntry?.arguments?.getString(RouteKeys.NextScreen) ?: OtherScreen.Exercises.route
            CountdownScreen(
                onFinish = {
                    navController.navigateToAndClearStack(nextScreen)
                }
            )
        }

    }
}

fun navigateToCountdown(navController: NavHostController, nextScreen: String) {
    val route = "Countdown/$nextScreen"
    navController.navigate(route)
}


fun NavHostController.navigateToAndClearStack(destination: String) {
    val currentBackStackEntry = this.currentBackStackEntry
    val currentRoute = currentBackStackEntry?.destination?.route
    if (currentRoute != null) {
        popBackStack(currentRoute, true)
    }
    navigate(destination) {
        popUpTo(graph.startDestinationId) { inclusive = false }
    }
}
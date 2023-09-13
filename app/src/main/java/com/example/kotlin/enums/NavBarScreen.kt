package com.example.kotlin.enums

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cognicraft.R

//enum class NavBarScreen(
//    val route: String,
//    val icon: ImageVector
//) {
//    Home("Home", Icons.Filled.Home),
//    Learn("Learn", Icons.Filled.Info),
//    Stats("Stats", Icons.Filled.Star),
//    Profile("Profile", Icons.Filled.Face),
//}

enum class NavBarScreen(
    @StringRes val routeResId: Int,
    val route : String,
    val icon: ImageVector
) {
    Home(R.string.home, "Home",Icons.Filled.Home),
    Learn(R.string.learn, "Learn", Icons.Filled.Info),
    Stats(R.string.stats, "Stats", Icons.Filled.Star),
    Profile(R.string.profile, "Profile",Icons.Filled.Face),
}

enum class OtherScreen(val route: String) {
    Exercises("Exercises"),
    Fallacy("Fallacy"),
    Countdown("Countdown/{nextScreen}"), // updated this line
    // Add more here
}

object RouteKeys {
    const val NextScreen = "nextScreen"
}
package com.example.kotlin.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(
    val route: String,
    val icon: ImageVector
) {
    Home("Home", Icons.Filled.Home),
    Learn("Learn", Icons.Filled.Info),
    Stats("Stats", Icons.Filled.Star),
    Profile("Profile", Icons.Filled.Face)
}
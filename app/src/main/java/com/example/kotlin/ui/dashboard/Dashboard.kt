package com.example.kotlin.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.kotlin.enums.NavBarScreen
import com.example.kotlin.ui.AppNavigator
import com.example.kotlin.ui.shared.NavigationBar
import com.example.kotlin.viewmodels.NavigationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard() {

    val navController = rememberNavController()
    val navViewModel: NavigationViewModel = hiltViewModel()

    val selectedScreen by navViewModel.selectedItem.collectAsState(initial = NavBarScreen.Home)
    val showNavBar = remember { mutableStateOf(true) }

    Scaffold(
            bottomBar = {
                NavigationBar(
                    navController = navController,
                    selectedItem = selectedScreen ?: NavBarScreen.Home,
                    onItemSelected = { index ->
                        navViewModel.updateSelectedItem(index)
                    },
                    items = navViewModel.bottomNavItems,
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                AppNavigator(navController, showNavBar)
            }
        }
    }

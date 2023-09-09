package com.example.kotlin.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.kotlin.enums.Screen
import com.example.kotlin.ui.AppNavigator
import com.example.kotlin.ui.shared.NavigationBarSample
import com.example.kotlin.viewmodels.NavigationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(navController: NavHostController, viewModel: NavigationViewModel) {

        val selectedScreen by viewModel.selectedItem.observeAsState(initial = Screen.Home)

        Scaffold(
            bottomBar = {
                NavigationBarSample(
                    navController = navController,
                    selectedItem = selectedScreen,
                    onItemSelected = { index ->
                        val screen = viewModel.bottomNavItems[index]
                        viewModel.updateSelectedItem(index)
                    },
                    items = viewModel.bottomNavItems
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                AppNavigator(navController)
            }
        }
    }

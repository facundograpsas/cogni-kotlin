package com.example.kotlin.ui.shared

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kotlin.enums.NavBarScreen
import com.example.kotlin.viewmodels.NavigationViewModel

@Composable
fun NavigationBar(navController: NavHostController, selectedItem: NavBarScreen, onItemSelected: (Int) -> Unit, items: List<NavBarScreen>) {
    val navViewModel: NavigationViewModel = hiltViewModel()
    val showNavBar by navViewModel.showNavBar.observeAsState(initial = true)

    if(showNavBar){
        NavigationBar(
            modifier = Modifier.height(60.dp),
            containerColor = NavigationBarDefaults.containerColor,
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround // or SpaceEvenly
            ) {
                items.forEachIndexed { index, item ->
                    CustomNavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.name) },
                        label = { Text(item.name) },
                        selected = selectedItem == item,
                        onClick = {
                            onItemSelected(index)
                            if (item.route == NavBarScreen.Home.route) {
                                navController.popBackStack("Home", false)
                            } else {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun RowScope.CustomNavigationBarItem(
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.background else Color.Transparent, label = ""
    )
    Box(
        Modifier
            .weight(1f)
            .fillMaxHeight()
            .background(backgroundColor)
    ){
        Column(
            Modifier
                .fillMaxSize()
                .clickable(onClick = onClick)
                .background(backgroundColor)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            icon()
            label()
        }
    }
}
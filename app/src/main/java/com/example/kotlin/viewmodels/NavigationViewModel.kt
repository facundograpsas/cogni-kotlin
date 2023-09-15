package com.example.kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.enums.NavBarScreen
import com.example.kotlin.enums.OtherScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {

    val bottomNavItems = listOf(NavBarScreen.Home, NavBarScreen.Learn, NavBarScreen.Stats, NavBarScreen.Profile)

    private val _selectedItem = MutableStateFlow<NavBarScreen?>(null)
    val selectedItem: StateFlow<NavBarScreen?> = _selectedItem.asStateFlow()

    fun updateSelectedItem(index: Int) {
        _selectedItem.value = bottomNavItems[index]
    }

    val routesWithoutNavBar = listOf(
        OtherScreen.Fallacy.route,
        OtherScreen.Countdown.route
    )

    // Added logic for showing/hiding NavBar
    private val _showNavBar = MutableStateFlow(true)
    val showNavBar: StateFlow<Boolean> = _showNavBar.asStateFlow()


    fun updateCurrentRoute(route: String) {
        _showNavBar.value = !routesWithoutNavBar.contains(route)
        Log.i("NavigationViewModel", _showNavBar.value.toString())
    }
}

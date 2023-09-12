package com.example.kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.enums.NavBarScreen
import com.example.kotlin.enums.OtherScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {


    val bottomNavItems = listOf(NavBarScreen.Home, NavBarScreen.Learn, NavBarScreen.Stats, NavBarScreen.Profile)
    private val _selectedItem = MutableLiveData<NavBarScreen>()
    val selectedItem: LiveData<NavBarScreen> get() = _selectedItem
    fun updateSelectedItem(index: Int) {
        _selectedItem.value = bottomNavItems[index]
    }



    val routesWithoutNavBar = listOf(
        OtherScreen.Fallacy.route,
        OtherScreen.Countdown.route
    )


    // Added logic for showing/hiding NavBar
    private val _showNavBar = MutableLiveData(true)
    val showNavBar: LiveData<Boolean> get() = _showNavBar

    fun hideNavBar() {
        _showNavBar.value = false
    }

    fun showNavBar() {
        _showNavBar.value = true
    }
    // Inside NavigationViewModel
    fun updateCurrentRoute(route: String) {
        _showNavBar.value = !routesWithoutNavBar.contains(route)

        Log.i("NavigationViewModel", _showNavBar.value.toString())
    }

}
package com.example.kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.enums.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {

    val bottomNavItems = listOf(Screen.Home, Screen.Learn, Screen.Stats, Screen.Profile)

    private val _selectedItem = MutableLiveData<Screen>()
    val selectedItem: LiveData<Screen> get() = _selectedItem

    fun updateSelectedItem(index: Int) {
        _selectedItem.value = bottomNavItems[index]
    }
}
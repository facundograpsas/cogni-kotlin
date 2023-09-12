package com.example.kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.data.TipsRepository
import com.example.kotlin.enums.NavBarScreen
import com.example.kotlin.enums.OtherScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val tipsRepository: TipsRepository) : ViewModel() {

    private val _randomTip = MutableLiveData<String>()
    val randomTip: LiveData<String> get() = _randomTip

    init {
        _randomTip.value = getRandomTip()
    }

    fun getRandomTip(): String {
        val tips = tipsRepository.getDailyTips()
        return tips.random()
    }

}
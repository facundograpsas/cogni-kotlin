package com.example.kotlin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.kotlin.repositories.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository) : ViewModel() {

}
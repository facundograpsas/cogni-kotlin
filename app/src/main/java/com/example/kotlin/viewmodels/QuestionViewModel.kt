package com.example.kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.models.db.QuestionEntity
import com.example.kotlin.repositories.QuestionRepository
import com.example.kotlin.models.network.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository) : ViewModel() {

    fun insertQuestion(questionEntity: QuestionEntity) = viewModelScope.launch(Dispatchers.IO) {
        Log.i("ViewModel", "saveQuestion: saving question...")
        questionRepository.insertQuestion(questionEntity)
    }
}
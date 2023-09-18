package com.example.kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.models.network.Question
import com.example.kotlin.repositories.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class InitState {
    object Loading : InitState()
    object InitComplete : InitState()
    data class Failure(val error: Throwable) : InitState()
}

@HiltViewModel
class InitViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    private val _state = MutableStateFlow<InitState>(InitState.Loading)
    val state: StateFlow<InitState> = _state

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    init {
        fetchQuestions()
    }

    private fun fetchQuestions() {
        viewModelScope.launch(Dispatchers.IO) {  // Use IO thread for API call
            _state.emit(InitState.Loading)  // Emit loading state

            try {
                val questions = questionRepository.getQuestions()
                _questions.value = questions!!
                Log.i("Questions", questions.toString())
                withContext(Dispatchers.Main) {  // Switch to main thread to update UI
                    Log.i("Info", "fetchQuestions:$questions")
                    _state.emit(InitState.InitComplete)  // Emit setup complete state
                }
            } catch (e: Exception) {
                Log.e("Error", "Exception while fetching questions: ${e.localizedMessage}")
                _state.emit(InitState.Failure(e))  // Emit failure state
            }
        }
    }
}

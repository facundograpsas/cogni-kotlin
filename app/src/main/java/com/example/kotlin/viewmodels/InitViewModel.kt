package com.example.kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.mappers.QuestionMapper
import com.example.kotlin.models.network.Question
import com.example.kotlin.models.state.DataResult
import com.example.kotlin.repositories.QuestionRepository
import com.example.kotlin.utils.AppException
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

sealed class InitEvent {
    object StartFetch : InitEvent()
    object ApiFetchSuccess : InitEvent()
    object ApiFetchFailure : InitEvent()
    object DbFetchSuccess : InitEvent()
    object DbFetchFailure : InitEvent()
}

@HiltViewModel
class InitViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    private val _state = MutableStateFlow<InitState>(InitState.Loading)
    val state: StateFlow<InitState> = _state

    init {
        fetchQuestions()
    }

    private fun fetchQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            handleEvent(InitEvent.StartFetch)
            when (val apiResult = questionRepository.getQuestions()) {
                is DataResult.Success -> {
                    val questionsFromApi = apiResult.data
                    questionRepository.insertQuestions(QuestionMapper.toEntity(questionsFromApi))
                    handleEvent(InitEvent.ApiFetchSuccess)
                }
                is DataResult.Failure -> {
                    Log.e("ApiFailure", "API failure: ${apiResult.exception}")
                    handleEvent(InitEvent.ApiFetchFailure)
                    // Handle network errors and then try to fetch from DB
                    when (val dbResult = questionRepository.getQuestionsFromLocalDb()) {
                        is DataResult.Success -> {
                            val questionsFromDb = dbResult.data
                            if (questionsFromDb.isNotEmpty()) {
                                handleEvent(InitEvent.DbFetchSuccess)
                            } else {
                                Log.e("DbEmpty", "DB is empty.")
                                handleEvent(InitEvent.DbFetchFailure)
                            }
                        }
                        is DataResult.Failure -> {
                            Log.e("DbFailure", "DB failure: ${dbResult.exception}")
                            handleEvent(InitEvent.DbFetchFailure)
                        }
                    }
                }
            }
        }
    }


    private fun handleEvent(event: InitEvent) {
        when (event) {
            is InitEvent.StartFetch -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _state.emit(InitState.Loading)
                }
            }
            is InitEvent.ApiFetchSuccess -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _state.emit(InitState.InitComplete)
                }
            }
            is InitEvent.ApiFetchFailure, is InitEvent.DbFetchFailure -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _state.emit(InitState.Failure(AppException.DatabaseReadingError("Something went wrong")))
                }
            }
            is InitEvent.DbFetchSuccess -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _state.emit(InitState.InitComplete)
                }
            }
        }
    }

}

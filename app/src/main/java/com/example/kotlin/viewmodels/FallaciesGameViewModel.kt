package com.example.kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.models.db.QuestionEntity
import com.example.kotlin.models.state.DataResult
import com.example.kotlin.repositories.QuestionRepository
import com.example.kotlin.state.fallaciesState.FallaciesGameAction
import com.example.kotlin.state.fallaciesState.FallaciesGameData
import com.example.kotlin.state.fallaciesState.FallaciesGameStates
import com.example.kotlin.state.fallaciesState.FallaciesGameStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FallaciesGameViewModel @Inject constructor(private val questionRepository: QuestionRepository) : ViewModel() {

    private val _fallaciesGameData = MutableStateFlow(FallaciesGameData(currentQuestion = null, currentScore = 0, questions = null, currentIndex = 0))
    private val fallaciesGameData: StateFlow<FallaciesGameData> = _fallaciesGameData

    private val fallaciesGameStateManager = FallaciesGameStateManager()

    private val _fallaciesGameStates = MutableStateFlow<FallaciesGameStates>(FallaciesGameStates.Loading)
    val fallaciesGameStates: StateFlow<FallaciesGameStates> = _fallaciesGameStates

    init {
        viewModelScope.launch {
            dispatchAction(FallaciesGameAction.Start)
            fetchQuestionsIfNecessary()
        }
    }

    private suspend fun dispatchAction(action: FallaciesGameAction) {
        val newState = fallaciesGameStateManager.getNextState(_fallaciesGameStates.value, action)
        _fallaciesGameStates.emit(newState)
        when (newState) {
            is FallaciesGameStates.Loaded -> {
                _fallaciesGameData.value = FallaciesGameData(newState.question,newState.score, fallaciesGameData.value.questions, newState.index)
            }
            else -> {}
        }
    }

    fun handleNextQuestion(selectedAnswer: String) {
        viewModelScope.launch {
            nextQuestion(selectedAnswer)
        }
    }

    private fun fetchQuestionsIfNecessary() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("FetchQuestions", "Starting to fetch questions.")
            when (val result = questionRepository.fetchTenUnsolvedQuestions()) { // Changed this line
                is DataResult.Success -> {
                    _fallaciesGameData.value = FallaciesGameData(currentQuestion = result.data[0], currentScore = 0, questions = result.data, currentIndex = 0)
                    dispatchAction(FallaciesGameAction.QuestionsFetched(result.data[0], index = 0, score = 0, result.data))
                    Log.d("FetchQuestions", "Successfully fetched questions. Total questions: ${result.data.size}")
                    Log.d("FetchQuestions", "Questions list updated.")
                }
                is DataResult.Failure -> {
                    dispatchAction(FallaciesGameAction.FetchError("Failed to fetch questions"))
                    Log.e("FetchQuestions", "Failed to fetch questions. Error: ${result.exception.message}", result.exception)
                    // Handle error, probably by emitting a state to show a message to the user
                }
            }
        }
    }

suspend fun nextQuestion(selectedAnswer: String) {
    val currentGameData = fallaciesGameData.value
    var score = currentGameData.currentScore
    val currentQuestion = currentGameData.currentQuestion ?: return
    val newIndex = currentGameData.currentIndex + 1
    val questions = currentGameData.questions ?: return // Early exit if questions are null

    if (selectedAnswer == currentQuestion.correctAnswer) {
        score += 1
        updateQuestionSolvedStatus(currentQuestion)
    }

    if (newIndex < questions.size) {
        val newQuestion = questions[newIndex]
        dispatchAction(FallaciesGameAction.UpdateFallaciesGameData(newQuestion, score, newIndex, questions))
    } else {
        dispatchAction(FallaciesGameAction.CompleteFallaciesGame)
    }
}

    private fun updateQuestionSolvedStatus(question: QuestionEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            question.solved = true
            when (val result = questionRepository.updateQuestion(question)) {
                is DataResult.Success -> {
                    Log.d("UpdateQuestion", "Successfully updated question.")
                }
                is DataResult.Failure -> {
                    Log.e("UpdateQuestion", "Failed to update question. Error: ${result.exception.message}", result.exception)
                    // Handle error, probably by emitting a state to show a message to the user
                }
            }
        }
    }
}
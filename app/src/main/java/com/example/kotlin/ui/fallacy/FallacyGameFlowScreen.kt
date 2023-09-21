package com.example.kotlin.ui.fallacy

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kotlin.ui.AppEvent
import com.example.kotlin.ui.FallacyScreenEvent
import com.example.kotlin.viewmodels.FallaciesGameViewModel
import com.example.kotlin.state.fallaciesState.FallaciesGameStates

@Composable
fun FallacyGameFlowScreen(onEvent: (AppEvent) -> Unit) {
    val fallaciesViewModel: FallaciesGameViewModel = hiltViewModel()
    val gameState by fallaciesViewModel.fallaciesGameStates.collectAsState()

    val handleNextQuestion = { selectedAnswer: String ->
        fallaciesViewModel.handleNextQuestion(selectedAnswer) // Assume you have this method in your ViewModel
    }

    when (gameState) {
        is FallaciesGameStates.Loading -> {
            // Show loading UI
        }
        is FallaciesGameStates.Loaded -> {
            val (currentQuestion, currentIndex, score , questions) = gameState as FallaciesGameStates.Loaded
            FallacyScoreScreen(currentQuestion, currentIndex, questions, onNextQuestion = handleNextQuestion) { localEvent ->
                when (localEvent) {
                    FallacyScreenEvent.Leave -> onEvent(AppEvent.NavigateBack)
                    // Other events can be forwarded to ViewModel or handled here
                }
            }
        }
        is FallaciesGameStates.Complete -> {
            val (score , questions, userAnswers) = gameState as FallaciesGameStates.Complete
//            Box(modifier = Modifier) {
//                Text(text = "JUEGO FINALIZADO")
//            }
            FallacyScoreScreen(score = score, questions = questions, userAnswers = userAnswers)  { localEvent ->
                when (localEvent) {
                    FallacyScreenEvent.Leave -> onEvent(AppEvent.NavigateBack)
                    // Other events can be forwarded to ViewModel or handled here
                }
            }
        }
        is FallaciesGameStates.Failure -> {
        }
    }
}
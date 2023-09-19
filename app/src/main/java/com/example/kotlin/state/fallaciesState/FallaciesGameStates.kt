package com.example.kotlin.state.fallaciesState

import com.example.kotlin.models.db.QuestionEntity

sealed class FallaciesGameStates {
    data class Loaded(
        val question: QuestionEntity,
        val index: Int,
        val score : Int,
        val questions: List<QuestionEntity>?,
        val isUpdated: Boolean = false

        ) : FallaciesGameStates()
    object Loading : FallaciesGameStates()
    object Complete : FallaciesGameStates()
    data class Failure(val error: String) : FallaciesGameStates()
}
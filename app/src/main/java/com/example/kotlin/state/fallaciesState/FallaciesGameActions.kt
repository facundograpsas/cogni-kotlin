package com.example.kotlin.state.fallaciesState

import com.example.kotlin.models.db.QuestionEntity

sealed class FallaciesGameAction {
    object Start : FallaciesGameAction()
    data class UpdateFallaciesGameData(
        val newQuestion: QuestionEntity?,
        val newScore: Int,
        val newIndex: Int,
        val questions : List<QuestionEntity>,
        val userAnswers : MutableMap<String, String>
    ) : FallaciesGameAction()

    data class CompleteFallaciesGame(
        val score: Int,
        val questions : List<QuestionEntity>,
        val userAnswers : MutableMap<String, String>
    ) : FallaciesGameAction()

    data class QuestionsFetched(
        val question: QuestionEntity,
        val index: Int,
        val score : Int,
        val questions : List<QuestionEntity>,
        val userAnswers : MutableMap<String, String>
    ) : FallaciesGameAction()
    data class FetchError(val message: String) : FallaciesGameAction()
//    object CompleteFallaciesGame : FallaciesGameAction()
    // ... more actions
}
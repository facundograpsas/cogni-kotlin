package com.example.kotlin.state.fallaciesState

import com.example.kotlin.models.db.QuestionEntity

data class FallaciesGameData(
    var currentQuestion: QuestionEntity?,
    val currentScore: Int,
    val questions : List<QuestionEntity>?,
    val currentIndex : Int,
    val userAnswers: MutableMap<String, String> = mutableMapOf() // Add this line

    // ... anything else
)
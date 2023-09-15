package com.example.kotlin.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Option(
    @Json(name = "label") val label: String,
    @Json(name = "value") val value: String
)

@JsonClass(generateAdapter = true)
data class SubQuestion(
    @Json(name = "question") val question: String,
    @Json(name = "options") val options: List<Option>,
    @Json(name = "correctAnswer") val correctAnswer: String,
    @Json(name = "correctAttempts") val correctAttempts: Int = 0,
    @Json(name = "wrongAttempts") val wrongAttempts: Int = 0
)

@JsonClass(generateAdapter = true)
data class Question(
    @Json(name = "level") val level: String,
    @Json(name = "levelInt") val levelInt: Int,
    @Json(name = "type") val type: String,  // If you want to make this an Enum, you can
    @Json(name = "text") val text: String,
    @Json(name = "question") val question: String?,
    @Json(name = "options") val options: List<Option>?,
    @Json(name = "correctAnswer") val correctAnswer: String,
    @Json(name = "subQuestions") val subQuestions: List<SubQuestion>?,  // For advanced exercises
    @Json(name = "correctAttempts") val correctAttempts: Int = 0,
    @Json(name = "wrongAttempts") val wrongAttempts: Int = 0,
    @Json(name = "timestamps") val timestamps: TimeStamps?
)

@JsonClass(generateAdapter = true)
data class TimeStamps(
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "updatedAt") val updatedAt: String
)
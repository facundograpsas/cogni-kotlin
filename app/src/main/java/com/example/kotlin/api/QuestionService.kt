package com.example.kotlin.api


import com.example.kotlin.models.Question
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface QuestionService {

    @GET("questions/{questionId}")  // Replace with your actual endpoint
    fun getQuestion(@Path("questionId") questionId: String): Call<Question>

    @GET("questions")  // Replace with your actual endpoint
    fun getQuestions(): Call<List<Question>>

    // Add more API calls as needed
}
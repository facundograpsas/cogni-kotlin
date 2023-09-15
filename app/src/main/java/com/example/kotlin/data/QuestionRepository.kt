package com.example.kotlin.data

import android.util.Log
import com.example.kotlin.api.ApiClient
import com.example.kotlin.models.Question

class QuestionRepository {

    suspend fun getQuestion(questionId: String): Question? {
        return try {
            val response = ApiClient.questionService.getQuestion(questionId).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getQuestions(): List<Question>? {
        return try {
            val response = ApiClient.questionService.getQuestions().execute()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Log.i("info", "getQuestions: $body")
                    return body
                }
            }
            null
        } catch (e: Exception) {
            Log.e("error", "getQuestions failed: ${e.localizedMessage}")
            null
        }
    }

    // Add more methods to communicate with your API
}
package com.example.kotlin.repositories

import android.util.Log
import com.example.kotlin.api.ApiClient
import com.example.kotlin.database.QuestionDao
import com.example.kotlin.models.db.QuestionEntity
import com.example.kotlin.models.network.Question
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val questionDao: QuestionDao) {

    suspend fun insertQuestion(question: QuestionEntity) {
        return questionDao.insertQuestion(question)
    }


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
}
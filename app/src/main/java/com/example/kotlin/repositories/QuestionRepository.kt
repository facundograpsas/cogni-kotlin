package com.example.kotlin.repositories

import android.util.Log
import com.example.kotlin.api.ApiClient
import com.example.kotlin.database.QuestionDao
import com.example.kotlin.models.db.QuestionEntity
import com.example.kotlin.models.network.Question
import com.example.kotlin.models.state.DataResult
import com.example.kotlin.utils.AppException
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val questionDao: QuestionDao) {

    suspend fun insertQuestions(question: List<QuestionEntity>): DataResult<Unit> {
        return try {
            questionDao.insertQuestions(question)
            DataResult.Success(Unit)  // Indicates successful operation
        } catch (e: Exception) {
            Log.e("DatabaseInsertError", "Error while inserting questions: ${e.message}", e)
            DataResult.Failure(AppException.DatabaseInsertError("There's been an error trying to save the records in the database: ${e.message}"))
        }
    }

    suspend fun getQuestionsFromLocalDb(): DataResult<List<QuestionEntity>> {
        return try {
            val questions = questionDao.getAllQuestions()
            if (questions.isNotEmpty()) {
                DataResult.Success(questions)
            } else {
                DataResult.Failure(AppException.DatabaseEmptyError("The database has no records."))
            }
        } catch (e: Exception) {
            Log.e("DatabaseReadingError", "Error while reading questions: ${e.message}", e)
            DataResult.Failure(AppException.DatabaseReadingError("There's been an error trying to read the records from the database: ${e.message}"))
        }
    }

    suspend fun getQuestions(): DataResult<List<Question>> {
        return try {
            val response = ApiClient.questionService.getQuestions().execute()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    return DataResult.Success(body)
                } ?: DataResult.Failure(AppException.NetworkError("No body in the response. HTTP Code: ${response.code()}"))
            } else {
                DataResult.Failure(AppException.NetworkError("HTTP request failed. Code: ${response.code()}, Message: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("NetworkError", "Error while fetching questions: ${e.message}", e)
            DataResult.Failure(AppException.NetworkError("There's been a network error: ${e.message}"))
        }
    }

    private var cachedQuestions: List<QuestionEntity>? = null

    suspend fun fetchQuestionsIfNecessary(): DataResult<List<QuestionEntity>> {
        return cachedQuestions?.let {
            DataResult.Success(it)
        } ?: getQuestionsFromLocalDb().also { result ->
            if (result is DataResult.Success) {
                cachedQuestions = result.data
            }
        }
    }

    suspend fun updateQuestion(question: QuestionEntity): DataResult<Unit> {
        return try {
            questionDao.updateQuestion(question)
            DataResult.Success(Unit)  // Indicates successful operation
        } catch (e: Exception) {
            Log.e("DatabaseUpdateError", "Error while updating question: ${e.message}", e)
            DataResult.Failure(AppException.DatabaseUpdateError("There's been an error trying to update the record in the database: ${e.message}"))
        }
    }

    suspend fun fetchTenUnsolvedQuestions(): DataResult<List<QuestionEntity>> {
        return try {
            val questions = questionDao.getFirstTenUnsolvedQuestions()
            if (questions.isNotEmpty()) {
                DataResult.Success(questions)
            } else {
                DataResult.Failure(AppException.DatabaseEmptyError("No unsolved questions are available."))
            }
        } catch (e: Exception) {
            Log.e("DatabaseReadingError", "Error while fetching unsolved questions: ${e.message}", e)
            DataResult.Failure(AppException.DatabaseReadingError("There's been an error trying to read the records from the database: ${e.message}"))
        }
    }
}

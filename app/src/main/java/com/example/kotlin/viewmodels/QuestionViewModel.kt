package com.example.kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin.data.QuestionRepository
import com.example.kotlin.models.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor() : ViewModel() {

    private val questionRepository = QuestionRepository()

    private val _questionData = MutableLiveData<List<Question?>?>()
    val questionData: MutableLiveData<List<Question?>?> get() = _questionData

    fun fetchQuestions() {
        viewModelScope.launch(Dispatchers.IO) {  // Use IO thread for API call
            try {
                val question = questionRepository.getQuestions()
                withContext(Dispatchers.Main) {  // Switch to main thread to update UI
                    Log.i("Info", "fetchQuestions:${question} ")
                    _questionData.value = question
                }
            } catch (e: Exception) {
                Log.e("Error", "Exception while fetching questions: ${e.localizedMessage}")
            }
        }
    }

    fun makeManualApiCall() {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://10.0.2.2:3000/v1/questions")  // Replace with your actual endpoint
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Log failure message
                println("Failed to execute request: ${e.message}")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                // Log the server's response body or message
                println("Server response: ${response.body?.string()}")
            }
        })
    }



    // Add more methods here based on your app's needs
}
package com.example.kotlin.api

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {

    private const val BASE_URL = "http://192.168.1.9:3000/v1/"


    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    // OkHttp Client
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(CustomInterceptor())  // Adding custom interceptor
        .addInterceptor(logging)
        .build()

    // Retrofit Client
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(httpClient)
        .build()

    // Add your services here. For example, if you have a QuestionService
    val questionService: QuestionService = retrofit.create(QuestionService::class.java)
}

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i("CustomInterceptor", "This log confirms the call was initiated.")
        return chain.proceed(chain.request())
    }
}
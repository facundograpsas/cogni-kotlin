package com.example.kotlin.models.state

import com.example.kotlin.utils.AppException

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Failure(val exception: AppException) : DataResult<Nothing>()
}
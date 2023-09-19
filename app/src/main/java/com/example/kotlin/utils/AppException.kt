package com.example.kotlin.utils

sealed class AppException : Exception() {
    object DatabaseError : AppException()
    data class DatabaseInsertError(override val message: String) : AppException()
    data class DatabaseReadingError(override val message: String) : AppException()
    data class DatabaseEmptyError(override val message: String) : AppException()
    data class DatabaseUpdateError(override val message: String) : AppException()
    data class NetworkError(override val message: String) : AppException()

}
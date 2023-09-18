package com.example.kotlin.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlin.models.db.QuestionEntity


@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions")
    fun getAllQuestions(): List<QuestionEntity>

    @Insert
    fun insertQuestion(question: QuestionEntity)

    @Delete
    fun delete(question: QuestionEntity)
}
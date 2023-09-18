package com.example.kotlin.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlin.models.db.QuestionEntity


@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions")
    fun getAllQuestions(): List<QuestionEntity>

    @Insert
    fun insertQuestion(question: QuestionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestions(questions: List<QuestionEntity>)

    @Delete
    fun delete(question: QuestionEntity)
}
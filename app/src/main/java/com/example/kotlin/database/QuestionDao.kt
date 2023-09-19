package com.example.kotlin.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kotlin.models.db.QuestionEntity


@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions")
    fun getAllQuestions(): List<QuestionEntity>

    @Insert
    fun insertQuestion(question: QuestionEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertQuestions(questions: List<QuestionEntity>)

    @Update
    fun updateQuestion(question: QuestionEntity)

    @Delete
    fun delete(question: QuestionEntity)

    @Query("SELECT * FROM questions WHERE solved = 0 LIMIT 10")
    fun getFirstTenUnsolvedQuestions(): List<QuestionEntity>
}
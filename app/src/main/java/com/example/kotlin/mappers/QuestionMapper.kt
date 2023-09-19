package com.example.kotlin.mappers

import com.example.kotlin.models.db.QuestionEntity
import com.example.kotlin.models.network.Question
import com.example.kotlin.models.network.TimeStamps

object QuestionMapper {

    fun toEntity(question: Question): QuestionEntity {
        return QuestionEntity(
            backendId = question.backendId,
            solved = true,
            level = question.level,
            levelInt = question.levelInt,
            type = question.type,
            text = question.text,
            question = question.question,
            options = question.options, // Assuming this is serializable to a string
            correctAnswer = question.correctAnswer,
            subQuestions = question.subQuestions,
            correctAttempts = question.correctAttempts,
            wrongAttempts = question.wrongAttempts,
            createdAt = question.timestamps?.createdAt,
            updatedAt = question.timestamps?.updatedAt
        )
    }

    fun toModel(questionEntity: QuestionEntity): Question {
        return Question(
            backendId = questionEntity.backendId,
            level = questionEntity.level,
            levelInt = questionEntity.levelInt,
            type = questionEntity.type,
            text = questionEntity.text,
            question = questionEntity.question,
            options = null,
            correctAnswer = questionEntity.correctAnswer,
            subQuestions = null,
            correctAttempts = questionEntity.correctAttempts,
            wrongAttempts = questionEntity.wrongAttempts,
            timestamps = TimeStamps(
                createdAt = questionEntity.createdAt ?: "",
                updatedAt = questionEntity.updatedAt ?: ""
            )
        )
    }

    fun toEntity(questions: List<Question>): List<QuestionEntity> {
        return questions.map { toEntity(it) }
    }

    fun toModel(questionEntities: List<QuestionEntity>): List<Question> {
        return questionEntities.map { toModel(it) }
    }

}
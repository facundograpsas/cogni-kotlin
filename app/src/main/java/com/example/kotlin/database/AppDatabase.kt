package com.example.kotlin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlin.models.db.OptionConverter
import com.example.kotlin.models.db.QuestionEntity
import com.example.kotlin.models.db.SubQuestionConverter

@Database(entities = [QuestionEntity::class], version = 4)
@TypeConverters(OptionConverter::class, SubQuestionConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}
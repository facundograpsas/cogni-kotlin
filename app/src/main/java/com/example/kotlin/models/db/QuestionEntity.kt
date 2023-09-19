package com.example.kotlin.models.db

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.kotlin.models.network.Option
import com.example.kotlin.models.network.SubQuestion
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@Entity(tableName = "questions",
    indices = [Index(value = ["backendId"], unique = true)]
)
data class QuestionEntity(
    @PrimaryKey
    @ColumnInfo(name = "backendId") val backendId: String,  // This is the _id from MongoDB
    @ColumnInfo(name = "solved") var solved: Boolean = true,
    @ColumnInfo(name = "level") val level: String,
    @ColumnInfo(name = "levelInt") val levelInt: Int,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "question") val question: String?,
    @TypeConverters(OptionConverter::class)
    @ColumnInfo(name = "options") val options: List<Option>?,
    @ColumnInfo(name = "correctAnswer") val correctAnswer: String,
    @TypeConverters(SubQuestionConverter::class)
    @ColumnInfo(name = "subQuestions") val subQuestions: List<SubQuestion>?,
    @ColumnInfo(name = "correctAttempts") val correctAttempts: Int = 0,
    @ColumnInfo(name = "wrongAttempts") val wrongAttempts: Int = 0,
    @ColumnInfo(name = "createdAt") val createdAt: String?, // Assuming String format
    @ColumnInfo(name = "updatedAt") val updatedAt: String?
)

class OptionConverter {

    private val moshi = Moshi.Builder().build()
    private val listType = Types.newParameterizedType(List::class.java, Option::class.java)

    @TypeConverter
    fun fromOptionList(options: List<Option>?): String {
        Log.d("OptionConverterDebug", "Converting from Object: $options")
        val json = moshi.adapter<List<Option>>(listType).toJson(options)
        Log.d("OptionConverterDebug", "Converted to JSON: $json")
        return json
    }

    @TypeConverter
    fun toOptionList(optionString: String): List<Option>? {
        Log.d("OptionConverterDebug", "Converting from JSON: $optionString")
        val options = moshi.adapter<List<Option>>(listType).fromJson(optionString)
        Log.d("OptionConverterDebug", "Converted to Object: $options")
        return options
    }
}

class SubQuestionConverter {

    private val moshi = Moshi.Builder().build()
    private val listType = Types.newParameterizedType(List::class.java, SubQuestion::class.java)
    private val jsonAdapter: JsonAdapter<List<SubQuestion>> = moshi.adapter(listType)

    @TypeConverter
    fun subQuestionsToJson(subQuestions: List<SubQuestion>?): String {
        val json = jsonAdapter.toJson(subQuestions)
        Log.d("MoshiDebug", "Converted to JSON: $json")
        return json
    }

    @TypeConverter
    fun jsonToSubQuestions(json: String?): List<SubQuestion>? {
        Log.d("MoshiDebug", "Converting from JSON: $json")
        val subQuestions = jsonAdapter.fromJson(json.orEmpty())
        Log.d("MoshiDebug", "Converted to Object: $subQuestions")
        return subQuestions
    }
}
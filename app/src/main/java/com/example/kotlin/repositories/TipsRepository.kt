package com.example.kotlin.repositories

import android.content.Context
import com.example.cognicraft.R
import org.json.JSONObject
import javax.inject.Inject

class TipsRepository @Inject constructor(private val context: Context) {


    fun getDailyTips(): Array<String> {
        val inputStream = context.resources.openRawResource(R.raw.tips)
        val json = inputStream.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(json)
        val jsonArray = jsonObject.getJSONArray("tips")
        return Array(jsonArray.length()) { i -> jsonArray.getString(i) }
    }
}
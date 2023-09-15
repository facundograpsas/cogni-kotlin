package com.example.kotlin.ui.activities

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.ui.theme.MyApplicationTheme
import com.example.kotlin.ui.dashboard.Dashboard
import com.example.kotlin.viewmodels.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: QuestionViewModel by viewModels()

    override fun attachBaseContext(newBase: Context?) {
        // here you can set the desired locale
        val config = Configuration(newBase?.resources?.configuration)
        config.setLocale(Locale("es"))
        val newContext = newBase?.createConfigurationContext(config)
        super.attachBaseContext(newContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val config = Configuration(resources.configuration)
//        config.setLocale(Locale("es"))
//        createConfigurationContext(config)


        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Dashboard() }
                }
            }
        }
    }

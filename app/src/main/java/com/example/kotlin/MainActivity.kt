package com.example.kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.kotlin.ui.theme.MyApplicationTheme
import com.example.kotlin.ui.dashboard.Dashboard
import com.example.kotlin.viewmodels.NavigationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Text(text = "aklsjdlkasjdlk")
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Red) {
                    Text(text = "aklsjdlkasjdlk")

                    Dashboard(navController,viewModel)
                }
                }

            }
        }
    }

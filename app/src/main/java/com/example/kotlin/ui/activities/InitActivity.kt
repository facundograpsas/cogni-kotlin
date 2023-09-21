package com.example.kotlin.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cognicraft.R
import com.example.kotlin.ui.theme.MyApplicationTheme
import com.example.kotlin.viewmodels.InitState
import com.example.kotlin.viewmodels.InitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InitActivity : ComponentActivity() {

    private val initViewModel: InitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                InitScreen(initViewModel)
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    initViewModel.state.collect { state ->
                        when (state) {
                            InitState.InitComplete -> {
                                val intent = Intent(this@InitActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else -> {
                                // Do nothing for other states
                            }
                        }
                    }
                }
            }

    }
}

@Composable
fun InitScreen(viewModel: InitViewModel) {
    val state by viewModel.state.collectAsState()
    when (state) {
            InitState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),  // Add the background color here
                    contentAlignment = Alignment.Center
                ) {
                    // Set the background color
                    Image(
                        painter = painterResource(id = R.drawable.logo_vertical), // Replace with your image
                        contentDescription = "App logo or whatever",
                    )
                }
                // Show a loading spinner
            }
            InitState.InitComplete -> {
                // Optionally show a "Complete" message or just remain empty as you're navigating away
            }
            is InitState.Failure -> {

                // Show an error message or UI
                Box(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.error),  // Add the background color here
                    contentAlignment = Alignment.Center
                ) {
                    // Set the background color
                    Image(
                        painter = painterResource(id = R.drawable.logo_vertical), // Replace with your image
                        contentDescription = "App logo or whatever",
                    )
                }
            }
        }
    }
}
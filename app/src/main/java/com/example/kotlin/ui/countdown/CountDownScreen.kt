package com.example.kotlin.ui.countdown

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun CountdownScreen(onFinish: () -> Unit) {
    val countdownState = remember { mutableIntStateOf(3) } // Starts at 3
    val animatedScale = remember { Animatable(0f) }
    val words = listOf("Go!", "Go!", "Set", "Ready?")  // The words you want to display



    LaunchedEffect(true) {
        while (countdownState.intValue > 0) {
            animatedScale.snapTo(0f) // Reset to 0
            animatedScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(500),
            )
            delay(100)
            countdownState.intValue--
        }
        onFinish()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = if (countdownState.intValue > 0) words[countdownState.intValue] else "Let's go!",
            style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.scale(animatedScale.value)
        )
    }
}
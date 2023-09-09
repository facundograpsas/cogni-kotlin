package com.example.kotlin.ui.home

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cognicraft.R

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                ,
            contentAlignment = Alignment.Center
        ) {
            Text("CogniCraft", fontSize = 20.sp)
        }

        // Main scrollable content
        Box(
            modifier = Modifier
                .weight(1f)  // take up all available space
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation))

                if (composition != null) {
                    Log.d("HomeScreen", "Lottie composition is not null.")

                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier.size(200.dp)// To make sure it loops
                    )
                } else {
                    Log.d("HomeScreen", "Lottie composition is null.")
                }
                // Text Content
                Text(
                    "Your text of 100 or 200 words goes here.," +
                            "Your text of 100 or 200 words goes here." +
                            "Your text of 100 or 200 words goes here." +
                            "Your text of 100 or 200 words goes here." +
                            "Your text of 100 or 200 words goes here." +
                            "Your text of 100 or 200 words goes here.Your text of 100 or 200 words goes here.Your text of 100 or 200 words goes here."+
                    "Your text of 100 or 200 words goes here.Your text of 100 or 200 words goes here.Your text of 100 or 200 words goes here."+
                    "Your text of 100 or 200 words goes here.Your text of 100 or 200 words goes here.Your text of 100 or 200 words goes here.",

                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        // Bottom Row with buttons (cards)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ClickableCard(icon = Icons.Rounded.ArrowForward ,text = "Continue path")
            ClickableCard(icon = Icons.Rounded.Build ,text = "Exercises")
        }
    }
}

@Composable
fun RowScope.ClickableCard(text : String, icon : ImageVector) {

    val shape = RoundedCornerShape(12.dp) // Replace this with the actual shape you want
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
        shape = CardDefaults.elevatedShape,
        modifier = Modifier
            .padding(10.dp)
            .weight(1f)
            .clip(shape = shape)
            .size(100.dp)
            .clickable(
            ) { /* Do something */ }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(40.dp))
                Text(text, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
    }
}
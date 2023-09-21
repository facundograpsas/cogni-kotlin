package com.example.kotlin.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cognicraft.R
import com.example.kotlin.ui.home.components.ClickableCard
import com.example.kotlin.viewmodels.HomeViewModel
import com.example.kotlin.viewmodels.InitViewModel
import com.example.kotlin.viewmodels.QuestionViewModel

@Composable
fun HomeScreen(onNavigateToExercisesScreen: () -> Unit) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val randomTip by homeViewModel.randomTip.observeAsState("Loading tip...")
    val viewModel: QuestionViewModel = hiltViewModel()
    val initViewModel: InitViewModel = hiltViewModel()


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
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
                    randomTip,
                    textAlign = TextAlign.Start,
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
            ClickableCard(
                icon = Icons.Rounded.ArrowForward
                ,text = stringResource(R.string.continue_path)
                , onTap = {
                })
            ClickableCard(
                icon = Icons.Rounded.Build ,
                text = stringResource(R.string.exercises),
                onTap = onNavigateToExercisesScreen )
        }
    }
}

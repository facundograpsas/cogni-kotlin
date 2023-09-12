package com.example.kotlin.ui.fallacy

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kotlin.viewmodels.NavigationViewModel

@Composable
fun FallacyIdentificationGame() {
    var selectedFallacy by remember { mutableStateOf<String?>(null) }
    val fallacies = listOf("Strawman", "Ad Hominem", "Slippery Slope")

    val navViewModel: NavigationViewModel = hiltViewModel()


    DisposableEffect(Unit) {
        navViewModel.hideNavBar()
        onDispose {
            Log.i("Info", "On dispose")
            navViewModel.showNavBar()
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Card for displaying the fallacy
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Argument: \"We should ban violent video games because a recent study found that most criminals play them.\"\n" +
                        "Question: \"What fallacy is present in this argument?\"",
                modifier = Modifier.background(color = MaterialTheme.colorScheme.secondaryContainer).padding(16.dp),
                style = TextStyle(fontSize = 16.sp)
            )
        }

        // Radio buttons for fallacy options
        fallacies.forEach { fallacy ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedFallacy = fallacy }
            ) {
                RadioButton(
                    selected = selectedFallacy == fallacy,
                    onClick = { selectedFallacy = fallacy }
                )
                Text(
                    text = fallacy,
                    style = TextStyle(fontSize = 18.sp),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        // Submit button
        Button(
            onClick = { /* Logic to check the selected fallacy */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Submit")
        }
    }

}
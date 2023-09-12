package com.example.kotlin.ui.learn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cognicraft.R
import com.example.kotlin.enums.OtherScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesScreen(onNavigateToFallacyScreen : () -> Unit) {
    // Using a Scrollable Column to accommodate multiple cards
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // A List of mock data representing exercise title and description
        val exerciseList = listOf(
            Pair("Fallacy Identificationsdasdasdasd", "This is description 1"),
            Pair("Argument Analysis", "This is description 2"),
            Pair("Debate Simulation", "This is description 3")
        )
        exerciseList.forEach { exercise ->
            // Each card with a height of 100.dp and some horizontal padding
            ExerciseCard(
                imageResourceId = R.drawable.woman_thinking,
                title = exercise.first,
                description = exercise.second,
                onTap = onNavigateToFallacyScreen
            )
        }
    }
}



@Composable
fun ColumnScope.ExerciseCard(
    imageResourceId: Int,
    title: String,
    description: String,
    onTap: () -> Unit
) {
    Card(
        modifier = Modifier
            .weight(1f)
            .clickable { onTap() }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Image(
                    contentScale = ContentScale.FillHeight,
                    painter = painterResource(id = imageResourceId),
                    contentDescription = null,
                    modifier = Modifier
                )
            }
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .padding(16.dp)
                    .weight(2f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = title,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                )
                Text(text = description)
            }
        }
    }
}
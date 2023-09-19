package com.example.kotlin.ui.fallacy

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cognicraft.R
import com.example.kotlin.models.db.QuestionEntity
import com.example.kotlin.ui.FallacyScreenEvent


@Composable
fun FallacyIdentificationScreen(
    currentQuestion : QuestionEntity,
    currentQuestionIndex : Int,
    questions : List<QuestionEntity>?,
    onNextQuestion: (selectedAnswer : String) -> Unit,
    onEvent: (FallacyScreenEvent) -> Unit) {

    var selectedFallacy by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }


    val progress = if (questions?.isNotEmpty() == true) {
        currentQuestionIndex.toFloat() / questions.size.toFloat()
    } else {
        0f
    }



    BackHandler {
        showDialog = true
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Warning", color = Color.White) },
            text = { Text("Are you sure you want to leave?", color = Color.White) },
            containerColor = Color.Black,
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onEvent(FallacyScreenEvent.Leave)
                }) {
                    Text("LEAVE", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("NO", color = Color.White)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.secondaryContainer
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        LinearProgressIndicator(
            progress = progress,
            color = Color.Blue,  // Progress color
            trackColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .clip(RoundedCornerShape(20.dp)) // Rounded corners
                .padding(top = 8.dp, bottom = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        StyledCard(currentQuestion)
        Spacer(modifier = Modifier.weight(1f))

        currentQuestion.options!!.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedFallacy = option.value }
            ) {
                RadioButton(
                    selected = selectedFallacy == option.value,
                    onClick = { selectedFallacy = option.value }
                )
                Text(
                    text = option.value,
                    style = TextStyle(fontSize = 18.sp),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onNextQuestion(selectedFallacy!!)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            shape = RoundedCornerShape(12.dp),

        ) {
            Text("Next", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        }
    }
}


@Composable
fun StyledCard(currentQuestion: QuestionEntity) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
        ) {
            Text(
                text = currentQuestion.type,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
            Text(
                text = currentQuestion.text,
                style = TextStyle(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${stringResource(id = R.string.question_string)}:",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
            Text(
                text = currentQuestion.question!!,
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}
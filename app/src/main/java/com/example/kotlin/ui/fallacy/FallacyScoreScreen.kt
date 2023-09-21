package com.example.kotlin.ui.fallacy

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun FallacyScoreScreen(
    score : Int,
    questions : List<QuestionEntity>?,
    userAnswers : MutableMap<String, String>,
    onEvent: (FallacyScreenEvent) -> Unit) {

    var selectedFallacy by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }



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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.secondaryContainer
                    )
                )

            )

    ) {

        Column(
            Modifier
                .fillMaxSize()

                .padding(20.dp)

        ) {

            //    Text("Score:", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
            //    Text("${score}/${questions!!.size}", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {


                Text("Score:", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                Text(
                    "${score}/${questions!!.size}",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                questions!!.forEach { question ->
                    val selectedAnswer = userAnswers[question.backendId]
                    ScoreAnswerCard(question, selectedAnswer)
                }
            }
            Button(

                onClick = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(10.dp),
                shape = RoundedCornerShape(12.dp),

                ) {
                Text("Continuar", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
            }
        }
    }
}


@Composable
fun ScoreAnswerCard(currentQuestion: QuestionEntity, selectedAnswer: String?) {
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

            Text(
                text = "Correct answer:",
                style = TextStyle(fontSize = 16.sp)
            )
            Text(text = currentQuestion.correctAnswer, color = Color.Green, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))

            val answerColor = if (selectedAnswer == currentQuestion.correctAnswer) Color.Green else Color.Red

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your answer:",
                    style = TextStyle(fontSize = 16.sp)
                )
                Spacer(modifier = Modifier.width(8.dp)) // Optional space between the texts
                Text(
                    text = selectedAnswer!!,
                    color = answerColor,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
            }

        }
    }
}
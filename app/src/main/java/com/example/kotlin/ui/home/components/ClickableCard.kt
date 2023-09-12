package com.example.kotlin.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.ClickableCard(text : String, icon : ImageVector, onTap : () -> Unit) {

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
            ) {
                onTap()
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.White)
                Text(text, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
            }
        }
    }
}
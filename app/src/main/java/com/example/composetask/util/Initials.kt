package com.example.composetask.util


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun getInitials(username: String): String {
    return username
        .trim()
        .split(Regex("\\s+"))
        .filter { it.isNotBlank() }
        .take(2)
        .mapNotNull { word ->
            word.firstOrNull()?.uppercase()
        }
        .joinToString("")
        .ifBlank { "?" }
}



@Composable
fun InitialsAvatar(
    username: String,
    size: Dp = 120.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(text = getInitials(username),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.background
        )
    }
}
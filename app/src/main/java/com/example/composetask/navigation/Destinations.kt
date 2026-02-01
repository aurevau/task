package com.example.composetask.navigation

import androidx.compose.material.icons.filled.ChatBubbleOutline


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.ChatBubble
sealed class Destinations(
    val route: String,
    val icon: ImageVector,
    val label: String
) {

    object HOME : Destinations(
        route = "home",
        icon = Icons.Default.ChatBubbleOutline,
        label = "Chats"
    )

    object CHAT : Destinations(
        route = "chat_list",
        icon = Icons.Default.ChatBubbleOutline,
        label = "Chat"
    )

    object PROFILE : Destinations(
        route = "profile",
        icon = Icons.Default.Person,
        label = "Profile"
    )
}


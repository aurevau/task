package com.example.composetask.model

data class Message(
    val id: String = "",
    val senderId: String = "",
    val message: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val senderName: String? = null,
    val senderImage: String? = null,
    val ImageUrl: String? = null

)

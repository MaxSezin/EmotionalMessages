package com.example.emotionalmessages

data class Chats(
    var receiverId: String? = "",
    val senderId: String = "",
    val message: String = "",
    val timestamp: String = "",
    val seen: Boolean = false
)

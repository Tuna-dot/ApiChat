package com.example.telegram.models.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatModel(
    @SerialName("chatId")
    var chatId: Int? = null,
    @SerialName("id")
    var id: Int? = null,
    @SerialName("message")
    var message: String? = null,
    @SerialName("receiverId")
    var receiverId: String? = null,
    @SerialName("senderId")
    var senderId: String? = null,
    @SerialName("timestamp")
    var timestamp: String? = null
)

package com.example.telegram.models.repositories

import com.example.telegram.models.core.Either
import com.example.telegram.models.core.RetrofitClient
import com.example.telegram.models.model.ChatModel
import com.example.telegram.models.service.ChatService

class ChatRepository {

   private val chatService = RetrofitClient.chatService

    suspend fun getChat(chatId: Int):Either<Throwable, List<ChatModel>> =
        try {
            val response = chatService.getChat(chatId)
            Either.Success(response)
        }catch (e: Exception){
            Either.Error(e)
        }


    suspend fun sendMessage(chatId: Int, message: String, senderId: String, receiverId: String): ChatModel {
        return chatService.sendMessage(chatId, message, senderId, receiverId)
    }

    suspend fun deleteMessage(chatId: Int, messageId: Int) {
        chatService.deleteMessage(chatId, messageId)
    }

    suspend fun updateMessage(chatId: Int, messageId: Int, message: String): ChatModel {
        return chatService.updateMessage(chatId, messageId, message)
    }
}
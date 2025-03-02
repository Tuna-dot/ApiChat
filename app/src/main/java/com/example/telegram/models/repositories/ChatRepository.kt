package com.example.telegram.models.repositories

import com.example.telegram.models.core.Either
import com.example.telegram.models.core.RetrofitClient
import com.example.telegram.models.model.ChatModel
import com.example.telegram.models.service.ChatService
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val chatService: ChatService
){

    suspend fun getChat(chatId: Int): Either<Throwable, List<ChatModel>> =
        try {
            val response = chatService.getChat(chatId)
            Either.Success(response)
        } catch (e: Exception) {
            Either.Error(e)
        }


    suspend fun sendMessage(
        chatId: Int,
        message: String,
        senderId: String,
        receiverId: String
    ): Either<Throwable, ChatModel> =
          try {
              val response = chatService.sendMessage(chatId, message, senderId, receiverId)
              Either.Success(response)
          } catch (e: Exception) {
            Either.Error(e)
          }


    suspend fun deleteMessage(chatId: Int, messageId: Int): Either<Throwable, Unit> =
         try {
            val response = chatService.deleteMessage(chatId, messageId)
            Either.Success(response)
        } catch (e: Exception) {
            Either.Error(e)
        }


    suspend fun updateMessage(chatId: Int, messageId: Int, message: String): Either<Throwable, ChatModel>  =
         try {
            val response = chatService.updateMessage(chatId, messageId, message)
            Either.Success(response)
        } catch (e: Exception) {
            Either.Error(e)
        }

}
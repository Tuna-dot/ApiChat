package com.example.telegram.models.service

import com.example.telegram.models.model.ChatModel
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ChatService {

    @GET("chat/{chatId}")
    suspend fun getChat(
        @Path("chatId") chatId: Int
    ): List<ChatModel>

    @FormUrlEncoded
    @POST("chat/send")
    suspend fun sendMessage(
        @Field("chatId") chatId: Int,
        @Field("message") message: String,
        @Field("senderId") senderId: String,
        @Field("receiverId") receiverId: String
    ): ChatModel

    @DELETE("chat/{chatId}/message/{messageId}")
    suspend fun deleteMessage(
        @Path("chatId") chatId: Int,
        @Path("messageId") messageId: Int
    ): Unit

    @FormUrlEncoded
    @PUT("chat/{chatId}/message/{messageId}")
    suspend fun updateMessage(
        @Path("chatId") chatId: Int,
        @Path("messageId") messageId: Int,
        @Field("message") message: String
    ): ChatModel
}
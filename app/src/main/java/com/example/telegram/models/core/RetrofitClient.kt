package com.example.telegram.models.core


import com.example.telegram.BuildConfig
import com.example.telegram.models.service.ChatService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private  val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private  val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val retrofitService: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    val chatService: ChatService by lazy {
        retrofitService.create(ChatService::class.java)
    }
}
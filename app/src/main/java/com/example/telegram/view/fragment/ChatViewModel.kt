package com.example.telegram.view.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telegram.models.core.Either
import com.example.telegram.models.model.ChatModel
import com.example.telegram.models.repositories.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {

    private val _messages = MutableLiveData<List<ChatModel>>()
    val messages: LiveData<List<ChatModel>> get() = _messages

    private val _event = MutableLiveData<UiEvent>()
    val event: LiveData<UiEvent> get() = _event

    fun getChat(chatId: Int) {
        viewModelScope.launch {
            when (val result = repository.getChat(chatId)) {
                is Either.Success -> _messages.postValue(result.data)
                is Either.Error -> sendEvent(
                    UiEvent.ShowError(
                        result.error.message ?: "Unknown error"
                    )
                )
            }

        }
    }

    fun sendMessage(
        chatId: Int,
        message: String,
        senderId: String,
        receiverId: String
    ) {
        viewModelScope.launch {
            when (val result = repository.sendMessage(chatId, message, senderId, receiverId)) {
                is Either.Success -> {
                    getChat(chatId)
                    sendEvent(UiEvent.MessageSent("Сообщение отправлено"))
                }
                is Either.Error -> sendEvent(
                    UiEvent.ShowError(
                        result.error.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    fun deleteMessage(chatId: Int, messageId: Int) {
        viewModelScope.launch {
            when (val result = repository.deleteMessage(chatId, messageId)) {
                is Either.Success -> {
                    getChat(chatId)
                    sendEvent(UiEvent.MessageDeleted("Сообщение удалено"))
                    }
                is Either.Error ->{
                    sendEvent(
                        UiEvent.ShowError(
                            result.error.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }

    fun updateMessage(chatId: Int, messageId: Int, message: String){
        viewModelScope.launch {
            when (val result = repository.updateMessage(chatId, messageId, message)) {
                is Either.Success -> {
                    getChat(chatId)
                    sendEvent(UiEvent.MessageUpdated("Сообщение обновлено"))
                }
                is Either.Error -> sendEvent(
                    UiEvent.ShowError(
                        result.error.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    private fun sendEvent(event: UiEvent) {
        _event.postValue(event)
    }

    sealed class UiEvent {
        data class ShowError(val message: String) : UiEvent()
        data class MessageSent(val message: String) : UiEvent()
        data class MessageDeleted(val message: String) : UiEvent()
        data class MessageUpdated(val message: String) : UiEvent()
    }

}
package com.example.telegram.view.fragment

import android.app.usage.UsageEvents
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telegram.models.core.Either
import com.example.telegram.models.model.ChatModel
import com.example.telegram.models.repositories.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val repository = ChatRepository()

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
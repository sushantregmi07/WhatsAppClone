package com.example.whatsappclone.feature.conversation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclone.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val chatRepository: ChatRepository,
) : ViewModel() {

    val conversationId: String = requireNotNull(savedStateHandle["contactId"])

    private val _uiState = MutableStateFlow(ConversationUiState())
    val uiState: StateFlow<ConversationUiState> = _uiState

    init {
        combine(
            chatRepository.observeConversation(conversationId),
            chatRepository.observeMessages(conversationId),
        ) { conversation, messages ->
            _uiState.update { current ->
                current.copy(
                    contactName = conversation?.displayName ?: "",
                    avatarKey = conversation?.avatar,
                    messages = messages,
                    isLoading = false,
                )
            }
        }
            .launchIn(viewModelScope)
    }

    fun onComposerTextChanged(text: String) {
        _uiState.update { it.copy(composerText = text) }
    }

    fun sendMessage() {
        val text = _uiState.value.composerText.trim()
        if (text.isBlank()) return

        _uiState.update { it.copy(composerText = "") }

        viewModelScope.launch {
            chatRepository.sendTextMessage(conversationId, text)
        }
    }
}

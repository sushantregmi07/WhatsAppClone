package com.example.whatsappclone.feature.conversation

import com.example.whatsappclone.domain.model.AvatarKey
import com.example.whatsappclone.domain.model.Message

data class ConversationUiState(
    val contactName: String = "",
    val avatarKey: AvatarKey? = null,
    val messages: List<Message> = emptyList(),
    val composerText: String = "",
    val isLoading: Boolean = true,
)

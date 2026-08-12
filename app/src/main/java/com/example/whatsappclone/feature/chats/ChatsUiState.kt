package com.example.whatsappclone.feature.chats

import com.example.whatsappclone.domain.model.ConversationSummary

data class ChatsUiState(
    val conversations: List<ConversationSummary> = emptyList(),
    val isLoading: Boolean = true,
    val isEditMode: Boolean = false,
    val selectedIds: Set<String> = emptySet(),
    val showDeleteConfirmation: Boolean = false,
)

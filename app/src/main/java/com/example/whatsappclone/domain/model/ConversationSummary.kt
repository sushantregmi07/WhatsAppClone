package com.example.whatsappclone.domain.model

import java.time.Instant

data class ConversationSummary(
    val id: String,
    val displayName: String,
    val avatar: AvatarKey,
    val latestMessage: MessageContent?,
    val latestMessageAt: Instant?,
    val unreadCount: Int,
    val isMuted: Boolean,
    val isArchived: Boolean,
)

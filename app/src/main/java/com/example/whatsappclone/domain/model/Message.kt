package com.example.whatsappclone.domain.model

import java.time.Instant

data class Message(
    val id: String,
    val conversationId: String,
    val content: MessageContent,
    val sentAt: Instant,
    val direction: MessageDirection,
    val deliveryStatus: DeliveryStatus,
)

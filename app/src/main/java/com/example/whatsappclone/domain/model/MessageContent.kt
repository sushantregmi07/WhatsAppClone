package com.example.whatsappclone.domain.model

sealed interface MessageContent {
    data class Text(val value: String) : MessageContent
    data class Document(
        val fileName: String,
        val sizeLabel: String,
        val extension: String,
    ) : MessageContent
    data class Voice(val durationLabel: String) : MessageContent
    data object Photo : MessageContent
}

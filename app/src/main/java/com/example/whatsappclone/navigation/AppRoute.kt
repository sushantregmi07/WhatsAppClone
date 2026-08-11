package com.example.whatsappclone.navigation

import kotlinx.serialization.Serializable

@Serializable
data object PhoneAuthorizationRoute

@Serializable
data object ChatsRoute

@Serializable
data class ConversationRoute(val contactId: String)

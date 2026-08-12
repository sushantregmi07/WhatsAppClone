package com.example.whatsappclone.domain.repository

import com.example.whatsappclone.domain.model.ConversationSummary
import com.example.whatsappclone.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun observeConversations(): Flow<List<ConversationSummary>>
    fun observeConversation(conversationId: String): Flow<ConversationSummary?>
    fun observeMessages(conversationId: String): Flow<List<Message>>
    suspend fun sendTextMessage(conversationId: String, text: String)
    suspend fun setMuted(conversationId: String, muted: Boolean)
    suspend fun archiveConversations(ids: Set<String>)
    suspend fun markConversationsRead(ids: Set<String>)
    suspend fun clearConversation(conversationId: String)
    suspend fun deleteConversations(ids: Set<String>)
}

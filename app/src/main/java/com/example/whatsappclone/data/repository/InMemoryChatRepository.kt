package com.example.whatsappclone.data.repository

import com.example.whatsappclone.data.seed.ChatSeedData
import com.example.whatsappclone.domain.model.ConversationSummary
import com.example.whatsappclone.domain.model.DeliveryStatus
import com.example.whatsappclone.domain.model.Message
import com.example.whatsappclone.domain.model.MessageContent
import com.example.whatsappclone.domain.model.MessageDirection
import com.example.whatsappclone.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryChatRepository @Inject constructor() : ChatRepository {

    private val conversationsState = MutableStateFlow(
        ChatSeedData.conversations.associateBy { it.id }
    )

    private val messagesState = MutableStateFlow(
        ChatSeedData.marthaCraigMessages
            .groupBy { it.conversationId }
            .toMutableMap() as Map<String, List<Message>>
    )

    override fun observeConversations(): Flow<List<ConversationSummary>> =
        conversationsState.map { convMap ->
            convMap.values
                .filter { !it.isArchived }
                .sortedByDescending { it.latestMessageAt }
        }

    override fun observeConversation(conversationId: String): Flow<ConversationSummary?> =
        conversationsState.map { it[conversationId] }

    override fun observeMessages(conversationId: String): Flow<List<Message>> =
        messagesState.map { it[conversationId].orEmpty() }

    override suspend fun sendTextMessage(conversationId: String, text: String) {
        val trimmed = text.trim()
        if (trimmed.isBlank()) return

        val now = Instant.now()
        val message = Message(
            id = UUID.randomUUID().toString(),
            conversationId = conversationId,
            content = MessageContent.Text(trimmed),
            sentAt = now,
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        )

        messagesState.update { current ->
            val existing = current[conversationId].orEmpty()
            current.toMutableMap().apply {
                put(conversationId, existing + message)
            }
        }

        conversationsState.update { current ->
            val conv = current[conversationId] ?: return@update current
            current.toMutableMap().apply {
                put(
                    conversationId,
                    conv.copy(
                        latestMessage = message.content,
                        latestMessageAt = now,
                    ),
                )
            }
        }
    }

    override suspend fun setMuted(conversationId: String, muted: Boolean) {
        conversationsState.update { current ->
            val conv = current[conversationId] ?: return@update current
            current.toMutableMap().apply {
                put(conversationId, conv.copy(isMuted = muted))
            }
        }
    }

    override suspend fun archiveConversations(ids: Set<String>) {
        conversationsState.update { current ->
            current.toMutableMap().apply {
                for (id in ids) {
                    val conv = this[id] ?: continue
                    put(id, conv.copy(isArchived = true))
                }
            }
        }
    }

    override suspend fun markConversationsRead(ids: Set<String>) {
        conversationsState.update { current ->
            current.toMutableMap().apply {
                for (id in ids) {
                    val conv = this[id] ?: continue
                    put(id, conv.copy(unreadCount = 0))
                }
            }
        }
    }

    override suspend fun clearConversation(conversationId: String) {
        messagesState.update { current ->
            current.toMutableMap().apply {
                remove(conversationId)
            }
        }

        conversationsState.update { current ->
            val conv = current[conversationId] ?: return@update current
            current.toMutableMap().apply {
                put(
                    conversationId,
                    conv.copy(latestMessage = null, latestMessageAt = null),
                )
            }
        }
    }

    override suspend fun deleteConversations(ids: Set<String>) {
        messagesState.update { current ->
            current.toMutableMap().apply {
                for (id in ids) remove(id)
            }
        }

        conversationsState.update { current ->
            current.toMutableMap().apply {
                for (id in ids) remove(id)
            }
        }
    }
}

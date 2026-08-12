package com.example.whatsappclone.data.repository

import com.example.whatsappclone.data.seed.ChatSeedData
import com.example.whatsappclone.domain.model.MessageContent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class InMemoryChatRepositoryTest {

    private lateinit var repo: InMemoryChatRepository

    @Before
    fun setUp() {
        repo = InMemoryChatRepository()
    }

    // --- Initial data ---

    @Test
    fun initialConversations_emitsAllEightInDescendingTimestampOrder() = runTest {
        val conversations = repo.observeConversations().first()

        assertEquals(8, conversations.size)
        assertEquals("Martin Randolph", conversations[0].displayName)
        assertEquals("James Thornton", conversations[7].displayName)

        for (i in 0 until conversations.size - 1) {
            val current = conversations[i].latestMessageAt!!
            val next = conversations[i + 1].latestMessageAt!!
            assertTrue(
                "${conversations[i].displayName} should be before ${conversations[i + 1].displayName}",
                current >= next,
            )
        }
    }

    @Test
    fun initialMessages_marthaCraigHasTwelveMessages() = runTest {
        val messages = repo.observeMessages(ChatSeedData.ID_MARTHA).first()
        assertEquals(12, messages.size)
    }

    @Test
    fun initialMessages_everyUserHasSeededMessages() = runTest {
        val allIds = listOf(
            ChatSeedData.ID_MARTIN, ChatSeedData.ID_ELENA,
            ChatSeedData.ID_KAREN, ChatSeedData.ID_DANIEL,
            ChatSeedData.ID_MARTHA, ChatSeedData.ID_TABITHA,
            ChatSeedData.ID_PRIYA, ChatSeedData.ID_JAMES,
        )
        for (id in allIds) {
            val messages = repo.observeMessages(id).first()
            assertTrue("Conversation $id should have messages", messages.isNotEmpty())
        }
    }

    @Test
    fun initialMessages_martinHasFourMessages() = runTest {
        val messages = repo.observeMessages(ChatSeedData.ID_MARTIN).first()
        assertEquals(4, messages.size)
    }

    @Test
    fun initialMessages_messageIdsAreUniqueAcrossAllConversations() = runTest {
        val allIds = ChatSeedData.allMessages.values.flatten().map { it.id }
        assertEquals(allIds.size, allIds.toSet().size)
    }

    @Test
    fun initialMessages_areOrderedChronologically() = runTest {
        for ((convId, messages) in ChatSeedData.allMessages) {
            for (i in 0 until messages.size - 1) {
                assertTrue(
                    "Messages in $convId should be chronological at index $i",
                    messages[i].sentAt <= messages[i + 1].sentAt,
                )
            }
        }
    }

    @Test
    fun initialMessages_conversationIdsMatchMessageConversationIds() = runTest {
        for ((convId, messages) in ChatSeedData.allMessages) {
            for (msg in messages) {
                assertEquals(
                    "Message ${msg.id} should belong to $convId",
                    convId, msg.conversationId,
                )
            }
        }
    }

    @Test
    fun latestMessagePreview_matchesActualLastMessage() = runTest {
        val conversations = repo.observeConversations().first()
        for (conv in conversations) {
            val messages = repo.observeMessages(conv.id).first()
            if (messages.isEmpty()) continue
            val lastMsg = messages.last()

            assertEquals(
                "Preview for ${conv.displayName} should match latest message",
                lastMsg.content, conv.latestMessage,
            )
            assertEquals(
                "Preview time for ${conv.displayName} should match latest message",
                lastMsg.sentAt, conv.latestMessageAt,
            )
        }
    }

    @Test
    fun seedData_containsDocumentMessages() = runTest {
        val allMessages = ChatSeedData.allMessages.values.flatten()
        assertTrue(
            "Seed data should have at least one Document message",
            allMessages.any { it.content is MessageContent.Document },
        )
    }

    @Test
    fun seedData_containsOneLineAndTwoLineTextMessages() {
        val allTexts = ChatSeedData.allMessages.values.flatten()
            .mapNotNull { (it.content as? MessageContent.Text)?.value }
        assertTrue(
            "Should have a short one-line message",
            allTexts.any { it.length <= 30 },
        )
        assertTrue(
            "Should have a longer two-line message candidate",
            allTexts.any { it.length > 40 },
        )
    }

    // --- Send text message ---

    @Test
    fun sendTextMessage_appendsAndUpdatesPreview() = runTest {
        val beforeCount = repo.observeMessages(ChatSeedData.ID_MARTIN).first().size
        repo.sendTextMessage(ChatSeedData.ID_MARTIN, "Hello there!")

        val messages = repo.observeMessages(ChatSeedData.ID_MARTIN).first()
        assertEquals(beforeCount + 1, messages.size)
        assertEquals(
            MessageContent.Text("Hello there!"),
            messages.last().content,
        )

        val conv = repo.observeConversation(ChatSeedData.ID_MARTIN).first()
        assertNotNull(conv)
        assertEquals(MessageContent.Text("Hello there!"), conv!!.latestMessage)
    }

    @Test
    fun sendTextMessage_trimsWhitespace() = runTest {
        val beforeCount = repo.observeMessages(ChatSeedData.ID_MARTIN).first().size
        repo.sendTextMessage(ChatSeedData.ID_MARTIN, "  padded message  ")

        val messages = repo.observeMessages(ChatSeedData.ID_MARTIN).first()
        assertEquals(
            MessageContent.Text("padded message"),
            messages[beforeCount].content,
        )
    }

    @Test
    fun sendTextMessage_movesConversationToTop() = runTest {
        repo.sendTextMessage(ChatSeedData.ID_JAMES, "New message")

        val conversations = repo.observeConversations().first()
        assertEquals(ChatSeedData.ID_JAMES, conversations[0].id)
    }

    @Test
    fun sendTextMessage_updatesOnlyTargetConversationPreview() = runTest {
        val elenaPreviewBefore = repo.observeConversation(ChatSeedData.ID_ELENA).first()!!.latestMessage
        repo.sendTextMessage(ChatSeedData.ID_MARTIN, "Test message")

        val elenaPreviewAfter = repo.observeConversation(ChatSeedData.ID_ELENA).first()!!.latestMessage
        assertEquals(elenaPreviewBefore, elenaPreviewAfter)
    }

    // --- Blank message defense ---

    @Test
    fun sendTextMessage_blankIsRejected() = runTest {
        val before = repo.observeMessages(ChatSeedData.ID_MARTIN).first()
        repo.sendTextMessage(ChatSeedData.ID_MARTIN, "   ")
        val after = repo.observeMessages(ChatSeedData.ID_MARTIN).first()

        assertEquals(before.size, after.size)
    }

    @Test
    fun sendTextMessage_emptyIsRejected() = runTest {
        val before = repo.observeMessages(ChatSeedData.ID_MARTIN).first()
        repo.sendTextMessage(ChatSeedData.ID_MARTIN, "")
        val after = repo.observeMessages(ChatSeedData.ID_MARTIN).first()

        assertEquals(before.size, after.size)
    }

    // --- Archive ---

    @Test
    fun archiveConversations_hidesOnlySelected() = runTest {
        repo.archiveConversations(setOf(ChatSeedData.ID_MARTIN, ChatSeedData.ID_ELENA))

        val visible = repo.observeConversations().first()
        assertEquals(6, visible.size)
        assertTrue(visible.none { it.id == ChatSeedData.ID_MARTIN })
        assertTrue(visible.none { it.id == ChatSeedData.ID_ELENA })
    }

    @Test
    fun archiveConversations_archivedStillAccessibleDirectly() = runTest {
        repo.archiveConversations(setOf(ChatSeedData.ID_MARTIN))

        val conv = repo.observeConversation(ChatSeedData.ID_MARTIN).first()
        assertNotNull(conv)
        assertTrue(conv!!.isArchived)
    }

    // --- Mark Read ---

    @Test
    fun markConversationsRead_clearsUnreadCounts() = runTest {
        val before = repo.observeConversation(ChatSeedData.ID_TABITHA).first()
        assertEquals(1, before!!.unreadCount)

        repo.markConversationsRead(setOf(ChatSeedData.ID_TABITHA))

        val after = repo.observeConversation(ChatSeedData.ID_TABITHA).first()
        assertEquals(0, after!!.unreadCount)
    }

    @Test
    fun markConversationsRead_noEffectOnAlreadyRead() = runTest {
        repo.markConversationsRead(setOf(ChatSeedData.ID_MARTIN))

        val conv = repo.observeConversation(ChatSeedData.ID_MARTIN).first()
        assertEquals(0, conv!!.unreadCount)
    }

    // --- Clear ---

    @Test
    fun clearConversation_removesMessagesButKeepsConversation() = runTest {
        repo.clearConversation(ChatSeedData.ID_MARTHA)

        val messages = repo.observeMessages(ChatSeedData.ID_MARTHA).first()
        assertTrue(messages.isEmpty())

        val conv = repo.observeConversation(ChatSeedData.ID_MARTHA).first()
        assertNotNull(conv)
        assertNull(conv!!.latestMessage)
        assertNull(conv.latestMessageAt)
    }

    // --- Delete ---

    @Test
    fun deleteConversations_removesBothSummaryAndMessages() = runTest {
        repo.deleteConversations(setOf(ChatSeedData.ID_MARTHA))

        val conv = repo.observeConversation(ChatSeedData.ID_MARTHA).first()
        assertNull(conv)

        val messages = repo.observeMessages(ChatSeedData.ID_MARTHA).first()
        assertTrue(messages.isEmpty())

        val conversations = repo.observeConversations().first()
        assertEquals(7, conversations.size)
    }

    @Test
    fun deleteConversations_multipleAtOnce() = runTest {
        repo.deleteConversations(
            setOf(ChatSeedData.ID_MARTIN, ChatSeedData.ID_ELENA, ChatSeedData.ID_KAREN),
        )

        val conversations = repo.observeConversations().first()
        assertEquals(5, conversations.size)
    }

    // --- Determinism ---

    @Test
    fun seedData_isDeterministic() {
        val repo1 = InMemoryChatRepository()
        val repo2 = InMemoryChatRepository()

        val allIds = ChatSeedData.allMessages.keys
        for (id in allIds) {
            val msgs1 = ChatSeedData.allMessages[id]!!
            val msgs2 = ChatSeedData.allMessages[id]!!
            assertEquals(msgs1.size, msgs2.size)
            for (i in msgs1.indices) {
                assertEquals(msgs1[i].id, msgs2[i].id)
                assertEquals(msgs1[i].sentAt, msgs2[i].sentAt)
            }
        }
    }
}

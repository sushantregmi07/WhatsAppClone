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
    fun initialMessages_otherConversationsHaveNoMessages() = runTest {
        val messages = repo.observeMessages(ChatSeedData.ID_MARTIN).first()
        assertTrue(messages.isEmpty())
    }

    // --- Send text message ---

    @Test
    fun sendTextMessage_appendsAndUpdatesPreview() = runTest {
        repo.sendTextMessage(ChatSeedData.ID_MARTIN, "Hello there!")

        val messages = repo.observeMessages(ChatSeedData.ID_MARTIN).first()
        assertEquals(1, messages.size)
        assertEquals(
            MessageContent.Text("Hello there!"),
            messages[0].content,
        )

        val conv = repo.observeConversation(ChatSeedData.ID_MARTIN).first()
        assertNotNull(conv)
        assertEquals(MessageContent.Text("Hello there!"), conv!!.latestMessage)
    }

    @Test
    fun sendTextMessage_trimsWhitespace() = runTest {
        repo.sendTextMessage(ChatSeedData.ID_MARTIN, "  padded message  ")

        val messages = repo.observeMessages(ChatSeedData.ID_MARTIN).first()
        assertEquals(
            MessageContent.Text("padded message"),
            messages[0].content,
        )
    }

    @Test
    fun sendTextMessage_movesConversationToTop() = runTest {
        repo.sendTextMessage(ChatSeedData.ID_JAMES, "New message")

        val conversations = repo.observeConversations().first()
        assertEquals(ChatSeedData.ID_JAMES, conversations[0].id)
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
        repo.sendTextMessage(ChatSeedData.ID_MARTIN, "")
        val messages = repo.observeMessages(ChatSeedData.ID_MARTIN).first()
        assertTrue(messages.isEmpty())
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
}

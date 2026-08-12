package com.example.whatsappclone.feature.conversation

import androidx.lifecycle.SavedStateHandle
import com.example.whatsappclone.data.repository.InMemoryChatRepository
import com.example.whatsappclone.data.seed.ChatSeedData
import com.example.whatsappclone.domain.model.MessageContent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ConversationViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private lateinit var repository: InMemoryChatRepository

    private fun createViewModel(conversationId: String): ConversationViewModel {
        repository = InMemoryChatRepository()
        val savedStateHandle = SavedStateHandle(
            mapOf("contactId" to conversationId),
        )
        return ConversationViewModel(savedStateHandle, repository)
    }

    @Test
    fun initialLoad_populatesContactNameAndMessages() = runTest {
        val vm = createViewModel(ChatSeedData.ID_MARTHA)

        val state = vm.uiState.value
        assertEquals("Martha Craig", state.contactName)
        assertEquals(12, state.messages.size)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun unknownConversationId_doesNotCrash() = runTest {
        val vm = createViewModel("nonexistent_id")

        val state = vm.uiState.value
        assertEquals("", state.contactName)
        assertTrue(state.messages.isEmpty())
    }

    @Test
    fun blankSend_isRejected() = runTest {
        val vm = createViewModel(ChatSeedData.ID_MARTHA)

        vm.onComposerTextChanged("   ")
        vm.sendMessage()

        assertEquals(12, vm.uiState.value.messages.size)
        assertEquals("   ", vm.uiState.value.composerText)
    }

    @Test
    fun successfulSend_appendsMessageAndClearsComposer() = runTest {
        val vm = createViewModel(ChatSeedData.ID_MARTHA)

        vm.onComposerTextChanged("Hello from test!")
        vm.sendMessage()

        val state = vm.uiState.value
        assertEquals(13, state.messages.size)
        assertEquals("", state.composerText)

        val lastMessage = state.messages.last()
        assertEquals(
            MessageContent.Text("Hello from test!"),
            lastMessage.content,
        )
    }

    @Test
    fun composerTextUpdates_arePreserved() = runTest {
        val vm = createViewModel(ChatSeedData.ID_MARTHA)

        vm.onComposerTextChanged("Draft text")
        assertEquals("Draft text", vm.uiState.value.composerText)

        vm.onComposerTextChanged("Updated draft")
        assertEquals("Updated draft", vm.uiState.value.composerText)
    }

    @Test
    fun differentUsers_loadDifferentConversations() = runTest {
        val vmMartin = createViewModel(ChatSeedData.ID_MARTIN)
        val martinMessages = vmMartin.uiState.value.messages

        val vmElena = createViewModel(ChatSeedData.ID_ELENA)
        val elenaMessages = vmElena.uiState.value.messages

        assertEquals("Martin Randolph", vmMartin.uiState.value.contactName)
        assertEquals("Elena Morales", vmElena.uiState.value.contactName)
        assertTrue("Martin should have messages", martinMessages.isNotEmpty())
        assertTrue("Elena should have messages", elenaMessages.isNotEmpty())
        assertTrue(
            "Martin and Elena should have different messages",
            martinMessages.map { it.id }.toSet() != elenaMessages.map { it.id }.toSet(),
        )
    }

    @Test
    fun sendMessage_updatesConversationSummaryPreview() = runTest {
        val vm = createViewModel(ChatSeedData.ID_MARTHA)
        val uniqueText = "M10 round-trip test ${System.nanoTime()}"

        vm.onComposerTextChanged(uniqueText)
        vm.sendMessage()

        val summary = repository.observeConversations().first()
            .first { it.id == ChatSeedData.ID_MARTHA }
        assertEquals(uniqueText, (summary.latestMessage as? MessageContent.Text)?.value)
    }
}

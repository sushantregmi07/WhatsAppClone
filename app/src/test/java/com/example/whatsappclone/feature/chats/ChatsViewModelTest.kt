package com.example.whatsappclone.feature.chats

import com.example.whatsappclone.data.repository.InMemoryChatRepository
import com.example.whatsappclone.data.seed.ChatSeedData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: InMemoryChatRepository
    private lateinit var viewModel: ChatsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = InMemoryChatRepository()
        viewModel = ChatsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `default state is not in edit mode with empty selection`() = runTest {
        backgroundScope.launch(testDispatcher) { viewModel.uiState.collect {} }

        val state = viewModel.uiState.value
        assertFalse(state.isEditMode)
        assertTrue(state.selectedIds.isEmpty())
        assertFalse(state.showDeleteConfirmation)
    }

    @Test
    fun `onEditClick enters edit mode`() = runTest {
        backgroundScope.launch(testDispatcher) { viewModel.uiState.collect {} }

        viewModel.onEditClick()
        assertTrue(viewModel.uiState.value.isEditMode)
    }

    @Test
    fun `onDoneClick clears selection and exits edit mode`() = runTest {
        backgroundScope.launch(testDispatcher) { viewModel.uiState.collect {} }

        viewModel.onEditClick()
        viewModel.onToggleSelection(ChatSeedData.ID_MARTIN)
        viewModel.onDoneClick()

        val state = viewModel.uiState.value
        assertFalse(state.isEditMode)
        assertTrue(state.selectedIds.isEmpty())
    }

    @Test
    fun `onToggleSelection adds and removes IDs`() = runTest {
        backgroundScope.launch(testDispatcher) { viewModel.uiState.collect {} }

        viewModel.onEditClick()

        viewModel.onToggleSelection(ChatSeedData.ID_MARTIN)
        assertTrue(ChatSeedData.ID_MARTIN in viewModel.uiState.value.selectedIds)

        viewModel.onToggleSelection(ChatSeedData.ID_MARTHA)
        assertEquals(2, viewModel.uiState.value.selectedIds.size)

        viewModel.onToggleSelection(ChatSeedData.ID_MARTIN)
        assertFalse(ChatSeedData.ID_MARTIN in viewModel.uiState.value.selectedIds)
        assertEquals(1, viewModel.uiState.value.selectedIds.size)
    }

    @Test
    fun `archive with empty selection is no-op`() = runTest {
        backgroundScope.launch(testDispatcher) { viewModel.uiState.collect {} }

        viewModel.onEditClick()
        val countBefore = viewModel.uiState.value.conversations.size
        viewModel.onArchiveSelected()
        assertEquals(countBefore, viewModel.uiState.value.conversations.size)
        assertTrue(viewModel.uiState.value.isEditMode)
    }

    @Test
    fun `onArchiveSelected archives conversations and exits edit mode`() = runTest {
        backgroundScope.launch(testDispatcher) { viewModel.uiState.collect {} }

        viewModel.onEditClick()
        viewModel.onToggleSelection(ChatSeedData.ID_MARTIN)
        val countBefore = viewModel.uiState.value.conversations.size

        viewModel.onArchiveSelected()

        val state = viewModel.uiState.value
        assertFalse(state.isEditMode)
        assertTrue(state.selectedIds.isEmpty())
        assertEquals(countBefore - 1, state.conversations.size)
        assertFalse(state.conversations.any { it.id == ChatSeedData.ID_MARTIN })
    }

    @Test
    fun `onMarkAllRead clears unread counts and exits edit mode`() = runTest {
        backgroundScope.launch(testDispatcher) { viewModel.uiState.collect {} }

        viewModel.onEditClick()
        viewModel.onToggleSelection(ChatSeedData.ID_TABITHA)

        viewModel.onMarkAllRead()

        val state = viewModel.uiState.value
        assertFalse(state.isEditMode)
        val tabitha = state.conversations.first { it.id == ChatSeedData.ID_TABITHA }
        assertEquals(0, tabitha.unreadCount)
    }

    @Test
    fun `onDeleteRequested shows confirmation dialog`() = runTest {
        backgroundScope.launch(testDispatcher) { viewModel.uiState.collect {} }

        viewModel.onEditClick()
        viewModel.onToggleSelection(ChatSeedData.ID_MARTIN)

        viewModel.onDeleteRequested()

        assertTrue(viewModel.uiState.value.showDeleteConfirmation)
    }

    @Test
    fun `onDeleteRequested with empty selection is no-op`() = runTest {
        backgroundScope.launch(testDispatcher) { viewModel.uiState.collect {} }

        viewModel.onEditClick()
        viewModel.onDeleteRequested()
        assertFalse(viewModel.uiState.value.showDeleteConfirmation)
    }

    @Test
    fun `onDeleteDismissed hides confirmation dialog`() = runTest {
        backgroundScope.launch(testDispatcher) { viewModel.uiState.collect {} }

        viewModel.onEditClick()
        viewModel.onToggleSelection(ChatSeedData.ID_MARTIN)
        viewModel.onDeleteRequested()

        viewModel.onDeleteDismissed()
        assertFalse(viewModel.uiState.value.showDeleteConfirmation)
    }

    @Test
    fun `onDeleteConfirmed deletes conversations and exits edit mode`() = runTest {
        backgroundScope.launch(testDispatcher) { viewModel.uiState.collect {} }

        viewModel.onEditClick()
        viewModel.onToggleSelection(ChatSeedData.ID_MARTIN)
        val countBefore = viewModel.uiState.value.conversations.size

        viewModel.onDeleteConfirmed()

        val state = viewModel.uiState.value
        assertFalse(state.isEditMode)
        assertTrue(state.selectedIds.isEmpty())
        assertFalse(state.showDeleteConfirmation)
        assertEquals(countBefore - 1, state.conversations.size)
        assertFalse(state.conversations.any { it.id == ChatSeedData.ID_MARTIN })
    }
}

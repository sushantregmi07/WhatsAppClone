package com.example.whatsappclone.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclone.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private data class EditState(
    val isEditMode: Boolean = false,
    val selectedIds: Set<String> = emptySet(),
    val showDeleteConfirmation: Boolean = false,
)

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
) : ViewModel() {

    private val editState = MutableStateFlow(EditState())

    val uiState: StateFlow<ChatsUiState> = combine(
        chatRepository.observeConversations(),
        editState,
    ) { conversations, edit ->
        ChatsUiState(
            conversations = conversations,
            isLoading = false,
            isEditMode = edit.isEditMode,
            selectedIds = edit.selectedIds,
            showDeleteConfirmation = edit.showDeleteConfirmation,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ChatsUiState(),
    )

    fun onEditClick() {
        editState.update { it.copy(isEditMode = true) }
    }

    fun onDoneClick() {
        editState.update { EditState() }
    }

    fun onToggleSelection(id: String) {
        editState.update { current ->
            val newSet = if (id in current.selectedIds) {
                current.selectedIds - id
            } else {
                current.selectedIds + id
            }
            current.copy(selectedIds = newSet)
        }
    }

    fun onArchiveSelected() {
        val ids = editState.value.selectedIds
        if (ids.isEmpty()) return
        viewModelScope.launch {
            chatRepository.archiveConversations(ids)
            editState.update { EditState() }
        }
    }

    fun onMarkAllRead() {
        val ids = editState.value.selectedIds
        if (ids.isEmpty()) return
        viewModelScope.launch {
            chatRepository.markConversationsRead(ids)
            editState.update { EditState() }
        }
    }

    fun onDeleteRequested() {
        if (editState.value.selectedIds.isEmpty()) return
        editState.update { it.copy(showDeleteConfirmation = true) }
    }

    fun onDeleteConfirmed() {
        val ids = editState.value.selectedIds
        viewModelScope.launch {
            chatRepository.deleteConversations(ids)
            editState.update { EditState() }
        }
    }

    fun onDeleteDismissed() {
        editState.update { it.copy(showDeleteConfirmation = false) }
    }
}

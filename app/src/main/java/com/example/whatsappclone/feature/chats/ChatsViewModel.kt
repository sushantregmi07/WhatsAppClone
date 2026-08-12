package com.example.whatsappclone.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclone.domain.model.ConversationSummary
import com.example.whatsappclone.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private data class LocalState(
    val isEditMode: Boolean = false,
    val selectedIds: Set<String> = emptySet(),
    val showDeleteConfirmation: Boolean = false,
    val actionSheetTarget: ConversationSummary? = null,
    val showClearConfirmation: Boolean = false,
)

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
) : ViewModel() {

    private val localState = MutableStateFlow(LocalState())

    private val _exportEvent = MutableSharedFlow<String>()
    val exportEvent: SharedFlow<String> = _exportEvent

    val uiState: StateFlow<ChatsUiState> = combine(
        chatRepository.observeConversations(),
        localState,
    ) { conversations, local ->
        ChatsUiState(
            conversations = conversations,
            isLoading = false,
            isEditMode = local.isEditMode,
            selectedIds = local.selectedIds,
            showDeleteConfirmation = local.showDeleteConfirmation,
            actionSheetTarget = local.actionSheetTarget,
            showClearConfirmation = local.showClearConfirmation,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ChatsUiState(),
    )

    // -- Edit mode intents --

    fun onEditClick() {
        localState.update { it.copy(isEditMode = true) }
    }

    fun onDoneClick() {
        localState.update { LocalState() }
    }

    fun onToggleSelection(id: String) {
        localState.update { current ->
            val newSet = if (id in current.selectedIds) {
                current.selectedIds - id
            } else {
                current.selectedIds + id
            }
            current.copy(selectedIds = newSet)
        }
    }

    fun onArchiveSelected() {
        val ids = localState.value.selectedIds
        if (ids.isEmpty()) return
        viewModelScope.launch {
            chatRepository.archiveConversations(ids)
            localState.update { LocalState() }
        }
    }

    fun onMarkAllRead() {
        val ids = localState.value.selectedIds
        if (ids.isEmpty()) return
        viewModelScope.launch {
            chatRepository.markConversationsRead(ids)
            localState.update { LocalState() }
        }
    }

    fun onDeleteRequested() {
        if (localState.value.selectedIds.isEmpty()) return
        localState.update { it.copy(showDeleteConfirmation = true) }
    }

    fun onDeleteConfirmed() {
        val ids = localState.value.selectedIds
        viewModelScope.launch {
            chatRepository.deleteConversations(ids)
            localState.update { LocalState() }
        }
    }

    fun onDeleteDismissed() {
        localState.update { it.copy(showDeleteConfirmation = false) }
    }

    // -- Swipe action intents --

    fun onSwipeArchive(conversationId: String) {
        viewModelScope.launch {
            chatRepository.archiveConversations(setOf(conversationId))
        }
    }

    // -- Action sheet intents --

    fun onMoreClick(conversation: ConversationSummary) {
        localState.update { it.copy(actionSheetTarget = conversation) }
    }

    fun onDismissActionSheet() {
        localState.update { it.copy(actionSheetTarget = null, showClearConfirmation = false) }
    }

    fun onMuteToggle() {
        val target = localState.value.actionSheetTarget ?: return
        viewModelScope.launch {
            chatRepository.setMuted(target.id, !target.isMuted)
            localState.update { it.copy(actionSheetTarget = null) }
        }
    }

    fun onExportChat() {
        val target = localState.value.actionSheetTarget ?: return
        val exportText = "Chat with ${target.displayName}"
        viewModelScope.launch {
            _exportEvent.emit(exportText)
            localState.update { it.copy(actionSheetTarget = null) }
        }
    }

    fun onClearChatRequested() {
        localState.update { it.copy(showClearConfirmation = true) }
    }

    fun onClearChatConfirmed() {
        val target = localState.value.actionSheetTarget ?: return
        viewModelScope.launch {
            chatRepository.clearConversation(target.id)
            localState.update { it.copy(actionSheetTarget = null, showClearConfirmation = false) }
        }
    }

    fun onClearChatDismissed() {
        localState.update { it.copy(showClearConfirmation = false) }
    }

    fun onDeleteChatRequested() {
        val target = localState.value.actionSheetTarget ?: return
        localState.update {
            it.copy(
                selectedIds = setOf(target.id),
                showDeleteConfirmation = true,
            )
        }
    }

    fun onDeleteChatConfirmedFromSheet() {
        val target = localState.value.actionSheetTarget ?: return
        viewModelScope.launch {
            chatRepository.deleteConversations(setOf(target.id))
            localState.update { LocalState() }
        }
    }

    fun onDeleteChatDismissedFromSheet() {
        localState.update {
            it.copy(
                selectedIds = emptySet(),
                showDeleteConfirmation = false,
            )
        }
    }
}

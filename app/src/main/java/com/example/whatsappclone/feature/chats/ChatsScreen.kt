package com.example.whatsappclone.feature.chats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.whatsappclone.feature.chats.component.ChatsTopBar
import com.example.whatsappclone.feature.chats.component.ConversationRow
import com.example.whatsappclone.feature.chats.component.EditModeHeader
import com.example.whatsappclone.feature.chats.component.EditModeToolbar
import com.example.whatsappclone.feature.chats.component.WhatsAppBottomBar
import com.example.whatsappclone.ui.theme.DestructiveRed
import kotlinx.coroutines.launch

@Composable
fun ChatsScreen(
    uiState: ChatsUiState,
    onConversationClick: (conversationId: String) -> Unit,
    onEditClick: () -> Unit,
    onDoneClick: () -> Unit,
    onToggleSelection: (String) -> Unit,
    onArchiveSelected: () -> Unit,
    onMarkAllRead: () -> Unit,
    onDeleteRequested: () -> Unit,
    onDeleteConfirmed: () -> Unit,
    onDeleteDismissed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun showSnackbar(message: String) {
        scope.launch { snackbarHostState.showSnackbar(message) }
    }

    if (uiState.showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = onDeleteDismissed,
            title = { Text("Delete Conversations") },
            text = {
                Text("Are you sure you want to delete ${uiState.selectedIds.size} conversation(s)? This cannot be undone.")
            },
            confirmButton = {
                TextButton(onClick = onDeleteConfirmed) {
                    Text("Delete", color = DestructiveRed)
                }
            },
            dismissButton = {
                TextButton(onClick = onDeleteDismissed) {
                    Text("Cancel")
                }
            },
        )
    }

    Column(modifier = modifier.fillMaxSize()) {
        ChatsTopBar(
            isEditMode = uiState.isEditMode,
            onEditClick = onEditClick,
            onDoneClick = onDoneClick,
            onComposeClick = { showSnackbar("Not included in scope") },
        )

        if (uiState.isEditMode) {
            EditModeHeader()
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            itemsIndexed(
                items = uiState.conversations,
                key = { _, conv -> conv.id },
            ) { index, conversation ->
                ConversationRow(
                    conversation = conversation,
                    onClick = {
                        if (uiState.isEditMode) {
                            onToggleSelection(conversation.id)
                        } else {
                            onConversationClick(conversation.id)
                        }
                    },
                    isEditMode = uiState.isEditMode,
                    isSelected = conversation.id in uiState.selectedIds,
                    showDivider = index < uiState.conversations.size - 1,
                )
            }
        }

        SnackbarHost(hostState = snackbarHostState)

        if (uiState.isEditMode) {
            EditModeToolbar(
                hasSelection = uiState.selectedIds.isNotEmpty(),
                onArchiveClick = onArchiveSelected,
                onReadAllClick = onMarkAllRead,
                onDeleteClick = onDeleteRequested,
            )
        } else {
            WhatsAppBottomBar(
                onTabClick = { tabLabel ->
                    if (tabLabel != "Chats") {
                        showSnackbar("Not included in scope")
                    }
                },
            )
        }
    }
}

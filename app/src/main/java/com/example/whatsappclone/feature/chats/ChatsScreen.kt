package com.example.whatsappclone.feature.chats

import android.content.Intent
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.whatsappclone.domain.model.ConversationSummary
import com.example.whatsappclone.feature.chats.component.BroadcastNewGroupRow
import com.example.whatsappclone.feature.chats.component.ChatActionSheet
import com.example.whatsappclone.feature.chats.component.ChatsTopBar
import com.example.whatsappclone.feature.chats.component.ConversationRow
import com.example.whatsappclone.feature.chats.component.EditModeHeader
import com.example.whatsappclone.feature.chats.component.EditModeToolbar
import com.example.whatsappclone.feature.chats.component.EmptyChatsState
import com.example.whatsappclone.feature.chats.component.ShimmerRow
import com.example.whatsappclone.feature.chats.component.SwipeableRow
import com.example.whatsappclone.feature.chats.component.WhatsAppBottomBar
import com.example.whatsappclone.ui.theme.DestructiveRed
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun ChatsScreen(
    uiState: ChatsUiState,
    exportEvent: SharedFlow<String>,
    onConversationClick: (conversationId: String) -> Unit,
    onEditClick: () -> Unit,
    onDoneClick: () -> Unit,
    onToggleSelection: (String) -> Unit,
    onArchiveSelected: () -> Unit,
    onMarkAllRead: () -> Unit,
    onDeleteRequested: () -> Unit,
    onDeleteConfirmed: () -> Unit,
    onDeleteDismissed: () -> Unit,
    onSwipeArchive: (String) -> Unit,
    onMoreClick: (ConversationSummary) -> Unit,
    onDismissActionSheet: () -> Unit,
    onMuteToggle: () -> Unit,
    onContactInfoClick: () -> Unit,
    onExportChat: () -> Unit,
    onClearChatRequested: () -> Unit,
    onClearChatConfirmed: () -> Unit,
    onClearChatDismissed: () -> Unit,
    onDeleteChatRequested: () -> Unit,
    onDeleteChatConfirmedFromSheet: () -> Unit,
    onDeleteChatDismissedFromSheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var openRowId by remember { mutableStateOf<String?>(null) }

    fun showSnackbar(message: String) {
        scope.launch { snackbarHostState.showSnackbar(message) }
    }

    LaunchedEffect(Unit) {
        exportEvent.collect { text ->
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            context.startActivity(Intent.createChooser(sendIntent, "Export Chat"))
        }
    }

    LaunchedEffect(uiState.isEditMode) {
        if (uiState.isEditMode) openRowId = null
    }

    // Delete confirmation — edit mode bulk delete
    if (uiState.showDeleteConfirmation && uiState.actionSheetTarget == null) {
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

    // Delete confirmation — action sheet single delete
    if (uiState.showDeleteConfirmation && uiState.actionSheetTarget != null) {
        AlertDialog(
            onDismissRequest = onDeleteChatDismissedFromSheet,
            title = { Text("Delete Chat") },
            text = {
                Text("Are you sure you want to delete this chat with ${uiState.actionSheetTarget.displayName}? This cannot be undone.")
            },
            confirmButton = {
                TextButton(onClick = onDeleteChatConfirmedFromSheet) {
                    Text("Delete", color = DestructiveRed)
                }
            },
            dismissButton = {
                TextButton(onClick = onDeleteChatDismissedFromSheet) {
                    Text("Cancel")
                }
            },
        )
    }

    // Clear chat confirmation
    if (uiState.showClearConfirmation && uiState.actionSheetTarget != null) {
        AlertDialog(
            onDismissRequest = onClearChatDismissed,
            title = { Text("Clear Chat") },
            text = {
                Text("Are you sure you want to clear all messages with ${uiState.actionSheetTarget.displayName}?")
            },
            confirmButton = {
                TextButton(onClick = onClearChatConfirmed) {
                    Text("Clear", color = DestructiveRed)
                }
            },
            dismissButton = {
                TextButton(onClick = onClearChatDismissed) {
                    Text("Cancel")
                }
            },
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            ChatsTopBar(
                isEditMode = uiState.isEditMode,
                onEditClick = onEditClick,
                onDoneClick = onDoneClick,
                onComposeClick = { showSnackbar("Not included in scope") },
            )

            if (uiState.isEditMode) {
                EditModeHeader()
            } else if (!uiState.isLoading) {
                BroadcastNewGroupRow()
            }

            when {
                uiState.isLoading -> {
                    Column(modifier = Modifier.weight(1f)) {
                        repeat(8) { index ->
                            ShimmerRow(showDivider = index < 7)
                        }
                    }
                }
                !uiState.isEditMode && uiState.conversations.isEmpty() -> {
                    EmptyChatsState(modifier = Modifier.weight(1f))
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                    ) {
                        itemsIndexed(
                            items = uiState.conversations,
                            key = { _, conv -> conv.id },
                        ) { index, conversation ->
                            if (uiState.isEditMode) {
                                ConversationRow(
                                    conversation = conversation,
                                    onClick = { onToggleSelection(conversation.id) },
                                    isEditMode = true,
                                    isSelected = conversation.id in uiState.selectedIds,
                                    showDivider = index < uiState.conversations.size - 1,
                                )
                            } else {
                                SwipeableRow(
                                    rowId = conversation.id,
                                    isOpen = openRowId == conversation.id,
                                    onOpenChanged = { newId -> openRowId = newId },
                                    onMoreClick = { onMoreClick(conversation) },
                                    onArchiveClick = { onSwipeArchive(conversation.id) },
                                ) {
                                    ConversationRow(
                                        conversation = conversation,
                                        onClick = { onConversationClick(conversation.id) },
                                        showDivider = index < uiState.conversations.size - 1,
                                    )
                                }
                            }
                        }
                    }
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

        // Action sheet overlay
        if (uiState.actionSheetTarget != null) {
            ChatActionSheet(
                isMuted = uiState.actionSheetTarget.isMuted,
                onMuteClick = onMuteToggle,
                onContactInfoClick = {
                    onDismissActionSheet()
                    showSnackbar("Contact Info not in scope")
                },
                onExportChatClick = onExportChat,
                onClearChatClick = onClearChatRequested,
                onDeleteChatClick = onDeleteChatRequested,
                onDismiss = onDismissActionSheet,
            )
        }
    }
}

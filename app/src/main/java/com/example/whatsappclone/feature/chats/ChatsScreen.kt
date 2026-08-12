package com.example.whatsappclone.feature.chats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.whatsappclone.feature.chats.component.ChatsTopBar
import com.example.whatsappclone.feature.chats.component.ConversationRow
import com.example.whatsappclone.feature.chats.component.WhatsAppBottomBar
import kotlinx.coroutines.launch

@Composable
fun ChatsScreen(
    uiState: ChatsUiState,
    onConversationClick: (conversationId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun showSnackbar(message: String) {
        scope.launch { snackbarHostState.showSnackbar(message) }
    }

    Column(modifier = modifier.fillMaxSize()) {
        ChatsTopBar(
            onEditClick = { showSnackbar("Available in a later milestone") },
            onComposeClick = { showSnackbar("Not included in scope") },
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            itemsIndexed(
                items = uiState.conversations,
                key = { _, conv -> conv.id },
            ) { index, conversation ->
                ConversationRow(
                    conversation = conversation,
                    onClick = { onConversationClick(conversation.id) },
                    showDivider = index < uiState.conversations.size - 1,
                )
            }
        }

        SnackbarHost(hostState = snackbarHostState)

        WhatsAppBottomBar(
            onTabClick = { tabLabel ->
                if (tabLabel != "Chats") {
                    showSnackbar("Not included in scope")
                }
            },
        )
    }
}

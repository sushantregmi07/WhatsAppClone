package com.example.whatsappclone.feature.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.R
import com.example.whatsappclone.domain.model.Message
import com.example.whatsappclone.domain.model.MessageContent
import com.example.whatsappclone.domain.model.MessageDirection
import com.example.whatsappclone.feature.conversation.component.ChatComposer
import com.example.whatsappclone.feature.conversation.component.ConversationTopBar
import com.example.whatsappclone.feature.conversation.component.DateSeparator
import com.example.whatsappclone.feature.conversation.component.DocumentMessageBubble
import com.example.whatsappclone.feature.conversation.component.EmptyConversationState
import com.example.whatsappclone.feature.conversation.component.MessageBubble
import com.example.whatsappclone.feature.conversation.component.ReceivedVoiceMessageBubble
import com.example.whatsappclone.ui.theme.ActionBlue
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
private val dateSeparatorFormatter = DateTimeFormatter.ofPattern("EEE, MMM d", Locale.ENGLISH)

/**
 * Builds a flat list of display items (messages + date separators) from messages.
 */
private sealed interface ConversationItem {
    data class DateHeader(val dateText: String, val key: String) : ConversationItem
    data class MessageItem(val message: Message) : ConversationItem
}

private fun buildConversationItems(messages: List<Message>): List<ConversationItem> {
    if (messages.isEmpty()) return emptyList()

    val zone = ZoneId.systemDefault()
    val items = mutableListOf<ConversationItem>()
    var lastDate: String? = null

    for (message in messages) {
        val messageDate = message.sentAt.atZone(zone).toLocalDate()
        val dateLabel = messageDate.format(dateSeparatorFormatter)

        if (dateLabel != lastDate) {
            items.add(ConversationItem.DateHeader(dateLabel, "date_$dateLabel"))
            lastDate = dateLabel
        }

        items.add(ConversationItem.MessageItem(message))
    }

    return items
}

@Composable
fun ConversationScreen(
    uiState: ConversationUiState,
    onBackClick: () -> Unit,
    onComposerTextChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    fun showSnackbar(message: String) {
        scope.launch { snackbarHostState.showSnackbar(message) }
    }

    val conversationItems = remember(uiState.messages) {
        buildConversationItems(uiState.messages)
    }

    LaunchedEffect(uiState.messages.size) {
        if (conversationItems.isNotEmpty()) {
            listState.animateScrollToItem(conversationItems.size - 1)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        ConversationTopBar(
            contactName = uiState.contactName,
            avatarKey = uiState.avatarKey,
            onBackClick = onBackClick,
            onVideoCallClick = { showSnackbar("Not included in scope") },
            onPhoneCallClick = { showSnackbar("Not included in scope") },
        )

        Box(modifier = Modifier.weight(1f)) {
            Image(
                painter = painterResource(id = R.drawable.chat_wallpaper),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        color = ActionBlue,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
                uiState.messages.isEmpty() -> {
                    EmptyConversationState(modifier = Modifier.fillMaxSize())
                }
                else -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(
                            items = conversationItems,
                            key = { item ->
                                when (item) {
                                    is ConversationItem.DateHeader -> item.key
                                    is ConversationItem.MessageItem -> item.message.id
                                }
                            },
                        ) { item ->
                            when (item) {
                                is ConversationItem.DateHeader -> {
                                    DateSeparator(dateText = item.dateText)
                                }
                                is ConversationItem.MessageItem -> {
                                    val msg = item.message
                                    val timestamp = msg.sentAt
                                        .atZone(ZoneId.systemDefault())
                                        .format(timeFormatter)

                                    Spacer(modifier = Modifier.height(
                                        if (msg.direction == MessageDirection.RECEIVED) 4.dp else 2.dp
                                    ))

                                    when (val content = msg.content) {
                                        is MessageContent.Text -> {
                                            MessageBubble(
                                                text = content.value,
                                                timestamp = timestamp,
                                                direction = msg.direction,
                                                deliveryStatus = msg.deliveryStatus,
                                            )
                                        }
                                        is MessageContent.Document -> {
                                            DocumentMessageBubble(
                                                fileName = content.fileName,
                                                sizeLabel = content.sizeLabel,
                                                extension = content.extension,
                                                timestamp = timestamp,
                                                deliveryStatus = msg.deliveryStatus,
                                            )
                                        }
                                        is MessageContent.Voice -> {
                                            if (msg.direction == MessageDirection.RECEIVED) {
                                                ReceivedVoiceMessageBubble(
                                                    duration = content.durationLabel,
                                                    timestamp = timestamp,
                                                )
                                            } else {
                                                MessageBubble(
                                                    text = "Voice: ${content.durationLabel}",
                                                    timestamp = timestamp,
                                                    direction = msg.direction,
                                                    deliveryStatus = msg.deliveryStatus,
                                                )
                                            }
                                        }
                                        is MessageContent.Photo -> {
                                            MessageBubble(
                                                text = "Photo",
                                                timestamp = timestamp,
                                                direction = msg.direction,
                                                deliveryStatus = msg.deliveryStatus,
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(2.dp))
                                }
                            }
                        }
                    }
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }

        ChatComposer(
            text = uiState.composerText,
            onTextChanged = onComposerTextChanged,
            onSendClick = onSendClick,
        )
    }
}

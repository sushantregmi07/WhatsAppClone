package com.example.whatsappclone.feature.chats.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.domain.model.ConversationSummary
import com.example.whatsappclone.domain.model.DeliveryStatus
import com.example.whatsappclone.domain.model.MessageContent
import com.example.whatsappclone.domain.model.MessageDirection
import com.example.whatsappclone.ui.components.AvatarImage
import com.example.whatsappclone.ui.components.ReadReceiptIcon
import com.example.whatsappclone.ui.theme.ActionBlue
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.PhotoIndicatorGray
import com.example.whatsappclone.ui.theme.TextSecondary
import com.example.whatsappclone.ui.theme.VoiceIndicatorGreen
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ConversationRow(
    conversation: ConversationSummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    isSelected: Boolean = false,
    showDivider: Boolean = true,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .semantics(mergeDescendants = true) {}
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(
                    horizontal = Dimens.ChatRowHorizontalPadding,
                    vertical = Dimens.ChatRowVerticalPadding,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isEditMode) {
                CircularCheckbox(checked = isSelected)
                Spacer(modifier = Modifier.width(Dimens.SpacingSm))
            }

            AvatarImage(
                avatarKey = conversation.avatar,
                displayName = conversation.displayName,
                size = Dimens.AvatarSizeChatList,
            )

            Spacer(modifier = Modifier.width(Dimens.SpacingMd))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = conversation.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (conversation.unreadCount > 0) FontWeight.Bold else FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false),
                    )

                    Spacer(modifier = Modifier.width(Dimens.SpacingSm))

                    Text(
                        text = formatTimestamp(conversation.latestMessageAt),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        maxLines = 1,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    PreviewContent(
                        latestMessage = conversation.latestMessage,
                        unreadCount = conversation.unreadCount,
                        modifier = Modifier.weight(1f),
                    )

                    Spacer(modifier = Modifier.width(Dimens.SpacingSm))

                    if (conversation.unreadCount > 0) {
                        UnreadBadge(count = conversation.unreadCount)
                    }

                    if (!isEditMode) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
            }
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(start = Dimens.DividerInsetStart),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    }
}

@Composable
private fun PreviewContent(
    latestMessage: MessageContent?,
    unreadCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (latestMessage != null && unreadCount == 0) {
            val showReceipt = latestMessage !is MessageContent.Voice &&
                latestMessage !is MessageContent.Photo ||
                unreadCount == 0 && latestMessage is MessageContent.Text
            if (latestMessage is MessageContent.Text || latestMessage is MessageContent.Document) {
                ReadReceiptIcon(
                    isRead = true,
                    size = 14.dp,
                )
                Spacer(modifier = Modifier.width(Dimens.SpacingXs))
            }
        }

        when (latestMessage) {
            is MessageContent.Voice -> {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = "Voice message",
                    tint = VoiceIndicatorGreen,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(Dimens.SpacingXs))
                Text(
                    text = latestMessage.durationLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    maxLines = 1,
                )
            }
            is MessageContent.Photo -> {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = "Photo",
                    tint = PhotoIndicatorGray,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(Dimens.SpacingXs))
                Text(
                    text = "Photo",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    maxLines = 1,
                )
            }
            is MessageContent.Text -> {
                Text(
                    text = latestMessage.value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            is MessageContent.Document -> {
                Text(
                    text = "${latestMessage.fileName}.${latestMessage.extension}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            null -> {}
        }
    }
}

@Composable
private fun UnreadBadge(count: Int) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(ActionBlue, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = count.toString(),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

private val chatDateFormatter = DateTimeFormatter.ofPattern("M/dd/yy")

private fun formatTimestamp(instant: Instant?): String {
    if (instant == null) return ""
    return instant.atZone(ZoneId.systemDefault()).format(chatDateFormatter)
}

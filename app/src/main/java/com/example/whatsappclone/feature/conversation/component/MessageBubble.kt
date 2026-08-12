package com.example.whatsappclone.feature.conversation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.domain.model.DeliveryStatus
import com.example.whatsappclone.domain.model.MessageDirection
import com.example.whatsappclone.ui.components.ReadReceiptIcon
import com.example.whatsappclone.ui.theme.BubbleReceived
import com.example.whatsappclone.ui.theme.BubbleSent
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.ReceivedBubbleShape
import com.example.whatsappclone.ui.theme.SentBubbleShape
import com.example.whatsappclone.ui.theme.TextSecondary

private const val RECEIPT_INLINE_ID = "receipt"

@Composable
fun MessageBubble(
    text: String,
    timestamp: String,
    direction: MessageDirection,
    deliveryStatus: DeliveryStatus,
    modifier: Modifier = Modifier,
) {
    val isSent = direction == MessageDirection.SENT
    val maxWidth = (LocalConfiguration.current.screenWidthDp * Dimens.BubbleMaxWidthFraction).dp

    val annotatedText = buildAnnotatedString {
        append(text)
        append("  ")
        withStyle(SpanStyle(color = TextSecondary, fontSize = 11.sp)) {
            append(timestamp)
        }
        if (isSent) {
            append(" ")
            appendInlineContent(RECEIPT_INLINE_ID, "read")
        }
    }

    val inlineContent = if (isSent) {
        mapOf(
            RECEIPT_INLINE_ID to InlineTextContent(
                placeholder = Placeholder(
                    width = 16.sp,
                    height = 12.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
                ),
            ) {
                ReadReceiptIcon(
                    isRead = deliveryStatus == DeliveryStatus.READ,
                    size = 14.dp,
                )
            },
        )
    } else {
        emptyMap()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentAlignment = if (isSent) Alignment.CenterEnd else Alignment.CenterStart,
    ) {
        Text(
            text = annotatedText,
            inlineContent = inlineContent,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            ),
            modifier = Modifier
                .widthIn(max = maxWidth)
                .background(
                    color = if (isSent) BubbleSent else BubbleReceived,
                    shape = if (isSent) SentBubbleShape else ReceivedBubbleShape,
                )
                .padding(
                    horizontal = Dimens.BubblePaddingHorizontal,
                    vertical = Dimens.BubblePaddingVertical,
                ),
        )
    }
}

package com.example.whatsappclone.feature.conversation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentAlignment = if (isSent) Alignment.CenterEnd else Alignment.CenterStart,
    ) {
        Column(
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
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                    fontSize = 16.sp,
                ),
            )

            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    text = timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                )
                if (isSent) {
                    Spacer(modifier = Modifier.width(4.dp))
                    ReadReceiptIcon(
                        isRead = deliveryStatus == DeliveryStatus.READ,
                        size = 14.dp,
                    )
                }
            }
        }
    }
}

package com.example.whatsappclone.feature.conversation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.domain.model.DeliveryStatus
import com.example.whatsappclone.ui.components.DocumentFileIcon
import com.example.whatsappclone.ui.components.ReadReceiptIcon
import com.example.whatsappclone.ui.theme.BubbleSent
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.DocumentSizeGreen
import com.example.whatsappclone.ui.theme.DocumentSurfaceGreen
import com.example.whatsappclone.ui.theme.SentBubbleShape
import com.example.whatsappclone.ui.theme.TextSecondary
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun DocumentMessageBubble(
    fileName: String,
    sizeLabel: String,
    extension: String,
    timestamp: String,
    deliveryStatus: DeliveryStatus,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .background(
                    color = BubbleSent,
                    shape = SentBubbleShape,
                )
                .padding(6.dp),
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = DocumentSurfaceGreen,
                modifier = Modifier
                    .width(150.dp)
                    .height(42.dp),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    DocumentFileIcon(size = 24.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = fileName,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row {
                    Text(
                        text = sizeLabel,
                        fontFamily = FontFamily.Default,
                        fontSize = 11.sp,
                        color = DocumentSizeGreen,
                    )
                    Text(
                        text = " \u00B7 ",
                        fontFamily = FontFamily.Default,
                        fontSize = 11.sp,
                        color = DocumentSizeGreen,
                    )
                    Text(
                        text = extension,
                        fontFamily = FontFamily.Default,
                        fontSize = 11.sp,
                        color = DocumentSizeGreen,
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp,
                        color = TextSecondary,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    ReadReceiptIcon(
                        isRead = deliveryStatus == DeliveryStatus.READ,
                        size = 12.dp,
                    )
                }
            }
        }
    }
}

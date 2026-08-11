package com.example.whatsappclone.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.ui.components.AvatarPlaceholder
import com.example.whatsappclone.ui.components.CircularCheckbox
import com.example.whatsappclone.ui.components.DocumentFileIcon
import com.example.whatsappclone.ui.components.ReadReceiptIcon

@Preview(showBackground = true, widthDp = 360, heightDp = 900)
@Composable
private fun DesignSystemPreview() {
    WhatsAppCloneTheme {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            // --- Colors ---
            Text("Colors", style = MaterialTheme.typography.headlineLarge)
            ColorSwatchRow("Sent Bubble", BubbleSent)
            ColorSwatchRow("Received Bubble", BubbleReceived)
            ColorSwatchRow("Wallpaper BG", WallpaperBackground)
            ColorSwatchRow("Action Blue", ActionBlue)
            ColorSwatchRow("Read Receipt", ReadReceiptBlue)
            ColorSwatchRow("Voice Green (RC1)", VoiceIndicatorGreen)
            ColorSwatchRow("Destructive Red", DestructiveRed)
            ColorSwatchRow("Date Chip", DateChipBackground)
            ColorSwatchRow("Swipe Archive", SwipeArchiveBlue)
            ColorSwatchRow("Action Sheet", ActionSheetGroupedSurface)
            ColorSwatchRow("Divider", DividerColor)
            ColorSwatchRow("Doc Size Green", DocumentSizeGreen)

            // --- Typography ---
            Text("Typography", style = MaterialTheme.typography.headlineLarge)
            Text("Headline Large (34sp Bold)", style = MaterialTheme.typography.headlineLarge)
            Text("Title Medium (17sp SemiBold)", style = MaterialTheme.typography.titleMedium)
            Text("Body Large (16sp SemiBold)", style = MaterialTheme.typography.bodyLarge)
            Text("Body Medium (15sp Regular)", style = MaterialTheme.typography.bodyMedium)
            Text("Body Small (14sp Regular)", style = MaterialTheme.typography.bodySmall)
            Text("Label Small (12sp Regular)", style = MaterialTheme.typography.labelSmall)

            // --- Bubble Shapes ---
            Text("Bubble Shapes", style = MaterialTheme.typography.headlineLarge)
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Surface(
                    shape = SentBubbleShape,
                    color = BubbleSent,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        "Sent bubble",
                        modifier = Modifier.padding(Dimens.BubblePaddingHorizontal, Dimens.BubblePaddingVertical),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Surface(
                    shape = ReceivedBubbleShape,
                    color = BubbleReceived,
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, DividerColor, ReceivedBubbleShape),
                ) {
                    Text(
                        "Received bubble",
                        modifier = Modifier.padding(Dimens.BubblePaddingHorizontal, Dimens.BubblePaddingVertical),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            // --- Date Chip ---
            Surface(
                shape = DateChipShape,
                color = DateChipBackground,
            ) {
                Text(
                    "Fri, Jul 26",
                    modifier = Modifier.padding(
                        horizontal = Dimens.DateChipPaddingHorizontal,
                        vertical = Dimens.DateChipPaddingVertical,
                    ),
                    style = MaterialTheme.typography.labelSmall,
                )
            }

            // --- Custom Icons ---
            Text("Custom Icons", style = MaterialTheme.typography.headlineLarge)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ReadReceiptIcon(isRead = true, size = 24.dp)
                    Spacer(Modifier.height(4.dp))
                    Text("Read", style = MaterialTheme.typography.labelSmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ReadReceiptIcon(isRead = false, size = 24.dp)
                    Spacer(Modifier.height(4.dp))
                    Text("Delivered", style = MaterialTheme.typography.labelSmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    DocumentFileIcon(size = 40.dp)
                    Spacer(Modifier.height(4.dp))
                    Text("Document", style = MaterialTheme.typography.labelSmall)
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularCheckbox(checked = false, onCheckedChange = {})
                    Spacer(Modifier.height(4.dp))
                    Text("Unchecked", style = MaterialTheme.typography.labelSmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularCheckbox(checked = true, onCheckedChange = {})
                    Spacer(Modifier.height(4.dp))
                    Text("Checked", style = MaterialTheme.typography.labelSmall)
                }
            }

            // --- Avatar Placeholder ---
            Text("Avatar Placeholder (RC7)", style = MaterialTheme.typography.headlineLarge)
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AvatarPlaceholder("MR", size = Dimens.AvatarSizeChatList)
                AvatarPlaceholder("EC", size = Dimens.AvatarSizeChatList, backgroundColor = ActionBlue)
                AvatarPlaceholder("MC", size = Dimens.AvatarSizeConversationHeader)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ColorSwatchRow(label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color)
                .border(1.dp, DividerColor),
        )
        Text(label, style = MaterialTheme.typography.bodyMedium)
    }
}

package com.example.whatsappclone.feature.chats.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.ui.theme.ActionBlue
import com.example.whatsappclone.ui.theme.ActionSheetScrim
import com.example.whatsappclone.ui.theme.DestructiveRed
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.SystemGray5

private val CardShape = RoundedCornerShape(Dimens.ActionSheetCardRadius)
private val GroupedCardBackground = Color(0xFFECECED)

@Composable
fun ChatActionSheet(
    isMuted: Boolean,
    onMuteClick: () -> Unit,
    onContactInfoClick: () -> Unit,
    onExportChatClick: () -> Unit,
    onClearChatClick: () -> Unit,
    onDeleteChatClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    BackHandler(onBack = onDismiss)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ActionSheetScrim)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onDismiss,
            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = Dimens.ActionSheetHorizontalMargin)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(bottom = Dimens.SpacingSm)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {},
                ),
        ) {
            Surface(
                shape = CardShape,
                color = GroupedCardBackground,
            ) {
                Column {
                    ActionItem(
                        text = if (isMuted) "Unmute" else "Mute",
                        color = ActionBlue,
                        onClick = onMuteClick,
                    )
                    ActionDivider()
                    ActionItem(
                        text = "Contact Info",
                        color = ActionBlue,
                        onClick = onContactInfoClick,
                    )
                    ActionDivider()
                    ActionItem(
                        text = "Export Chat",
                        color = ActionBlue,
                        onClick = onExportChatClick,
                    )
                    ActionDivider()
                    ActionItem(
                        text = "Clear Chat",
                        color = ActionBlue,
                        onClick = onClearChatClick,
                    )
                    ActionDivider()
                    ActionItem(
                        text = "Delete Chat",
                        color = DestructiveRed,
                        onClick = onDeleteChatClick,
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.ActionSheetSpacing))

            Surface(
                shape = CardShape,
                color = Color.White,
            ) {
                ActionItem(
                    text = "Cancel",
                    color = ActionBlue,
                    fontWeight = FontWeight.SemiBold,
                    onClick = onDismiss,
                )
            }
        }
    }
}

@Composable
private fun ActionItem(
    text: String,
    color: Color,
    onClick: () -> Unit,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.ActionItemHeight)
            .background(Color.White)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = fontWeight,
            color = color,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ActionDivider() {
    HorizontalDivider(thickness = 0.5.dp, color = SystemGray5)
}

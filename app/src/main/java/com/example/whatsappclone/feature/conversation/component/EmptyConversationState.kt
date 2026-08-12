package com.example.whatsappclone.feature.conversation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.TextSecondary

@Composable
fun EmptyConversationState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.Chat,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(64.dp),
        )

        Spacer(modifier = Modifier.height(Dimens.SpacingMd))

        Text(
            text = "No messages yet",
            fontFamily = FontFamily.Default,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary,
        )

        Spacer(modifier = Modifier.height(Dimens.SpacingXs))

        Text(
            text = "Say hello!",
            fontFamily = FontFamily.Default,
            fontSize = 15.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

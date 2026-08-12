package com.example.whatsappclone.feature.conversation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.domain.model.AvatarKey
import com.example.whatsappclone.ui.components.AvatarImage
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.TextSecondary

private val TopBarSurface = Color(0xFFF6F6F6)

@Composable
fun ConversationTopBar(
    contactName: String,
    avatarKey: AvatarKey?,
    onBackClick: () -> Unit,
    onVideoCallClick: () -> Unit,
    onPhoneCallClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(TopBarSurface)
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(Dimens.TopBarHeight),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
            )
        }

        if (avatarKey != null) {
            AvatarImage(
                avatarKey = avatarKey,
                displayName = contactName,
                size = Dimens.AvatarSizeConversationHeader,
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = contactName,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
            Text(
                text = "tap here for contact info",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                maxLines = 1,
            )
        }

        IconButton(onClick = onVideoCallClick) {
            Icon(
                imageVector = Icons.Outlined.Videocam,
                contentDescription = "Video call",
            )
        }

        IconButton(onClick = onPhoneCallClick) {
            Icon(
                imageVector = Icons.Outlined.Call,
                contentDescription = "Phone call",
            )
        }
    }
}

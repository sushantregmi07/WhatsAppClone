package com.example.whatsappclone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.ui.theme.WhatsAppTealGreen

/**
 * Avatar fallback: colored circle with initials.
 *
 * Used when a drawable avatar asset is unavailable for a contact.
 * All 8 seed contacts now have Figma-exported avatars, but this
 * composable remains available for dynamically-added contacts or
 * error states where the drawable resource fails to load. (RC7)
 */
@Composable
fun AvatarPlaceholder(
    initials: String,
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
    backgroundColor: Color = WhatsAppTealGreen,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initials.take(2).uppercase(),
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = (size.value * 0.36f).sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AvatarPlaceholderPreview() {
    AvatarPlaceholder(initials = "MR", size = 50.dp)
}

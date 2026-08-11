package com.example.whatsappclone.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val SentBubbleShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 4.dp,
    bottomEnd = 16.dp,
    bottomStart = 16.dp,
)

val ReceivedBubbleShape = RoundedCornerShape(
    topStart = 4.dp,
    topEnd = 16.dp,
    bottomEnd = 16.dp,
    bottomStart = 16.dp,
)

val DateChipShape = RoundedCornerShape(8.dp)

val ActionSheetCardShape = RoundedCornerShape(14.dp)

val AvatarShape = CircleShape

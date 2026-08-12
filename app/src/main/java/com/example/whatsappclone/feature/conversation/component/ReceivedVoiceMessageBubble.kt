package com.example.whatsappclone.feature.conversation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.ui.theme.BubbleReceived
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.ReceivedBubbleShape
import com.example.whatsappclone.ui.theme.TextSecondary

@Composable
fun ReceivedVoiceMessageBubble(
    duration: String,
    timestamp: String,
    modifier: Modifier = Modifier,
) {
    val maxWidth = (LocalConfiguration.current.screenWidthDp * Dimens.BubbleMaxWidthFraction).dp

    val annotatedText = buildAnnotatedString {
        append("Voice: $duration")
        append("  ")
        withStyle(SpanStyle(color = TextSecondary, fontSize = 11.sp)) {
            append(timestamp)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = annotatedText,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            ),
            modifier = Modifier
                .widthIn(max = maxWidth)
                .background(
                    color = BubbleReceived,
                    shape = ReceivedBubbleShape,
                )
                .padding(
                    horizontal = 10.dp,
                    vertical = 6.dp,
                ),
        )
    }
}

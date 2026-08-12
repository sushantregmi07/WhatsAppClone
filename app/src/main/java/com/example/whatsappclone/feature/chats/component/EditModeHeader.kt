package com.example.whatsappclone.feature.chats.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.TextSecondary

@Composable
fun EditModeHeader(modifier: Modifier = Modifier) {
    Text(
        text = "Chats",
        fontFamily = FontFamily.Default,
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(
            horizontal = Dimens.ChatRowHorizontalPadding,
            vertical = Dimens.SpacingSm,
        ),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.ChatRowHorizontalPadding, vertical = Dimens.SpacingSm),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Broadcast Lists",
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            color = TextSecondary,
        )
        Text(
            text = "New Group",
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            color = TextSecondary,
        )
    }

    HorizontalDivider(thickness = 0.5.dp, color = TextSecondary.copy(alpha = 0.3f))
}

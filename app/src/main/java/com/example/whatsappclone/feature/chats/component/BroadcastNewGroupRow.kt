package com.example.whatsappclone.feature.chats.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.ui.theme.ActionBlue
import com.example.whatsappclone.ui.theme.Dimens

@Composable
fun BroadcastNewGroupRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.ChatRowHorizontalPadding, vertical = Dimens.SpacingSm),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Broadcast Lists",
            style = MaterialTheme.typography.titleMedium,
            color = ActionBlue,
        )
        Text(
            text = "New Group",
            style = MaterialTheme.typography.titleMedium,
            color = ActionBlue,
        )
    }

    HorizontalDivider(thickness = 0.5.dp, color = ActionBlue.copy(alpha = 0.15f))
}

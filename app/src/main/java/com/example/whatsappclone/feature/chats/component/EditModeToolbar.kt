package com.example.whatsappclone.feature.chats.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.ui.theme.ActionBlue
import com.example.whatsappclone.ui.theme.DestructiveRed
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.TextSecondary

private val ToolbarSurface = Color(0xFFF6F6F6)

@Composable
fun EditModeToolbar(
    hasSelection: Boolean,
    onArchiveClick: () -> Unit,
    onReadAllClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(ToolbarSurface)
            .windowInsetsPadding(WindowInsets.navigationBars),
    ) {
        HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFC6C6C8))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.BottomTabBarHeight),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(
                onClick = onArchiveClick,
                enabled = hasSelection,
            ) {
                Text(
                    text = "Archive",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (hasSelection) ActionBlue else TextSecondary,
                )
            }

            TextButton(
                onClick = onReadAllClick,
                enabled = hasSelection,
            ) {
                Text(
                    text = "Read All",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (hasSelection) ActionBlue else TextSecondary,
                )
            }

            TextButton(
                onClick = onDeleteClick,
                enabled = hasSelection,
            ) {
                Text(
                    text = "Delete",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (hasSelection) DestructiveRed else TextSecondary,
                )
            }
        }
    }
}

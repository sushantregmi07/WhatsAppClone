package com.example.whatsappclone.feature.chats.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.whatsappclone.ui.theme.ActionBlue
import com.example.whatsappclone.ui.theme.Dimens

private val TopBarSurface = Color(0xFFF6F6F6)

@Composable
fun ChatsTopBar(
    isEditMode: Boolean,
    onEditClick: () -> Unit,
    onDoneClick: () -> Unit,
    onComposeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(TopBarSurface)
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(Dimens.TopBarHeight),
    ) {
        if (isEditMode) {
            Text(
                text = "Done",
                style = MaterialTheme.typography.titleMedium,
                color = ActionBlue,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(onClick = onDoneClick)
                    .padding(horizontal = Dimens.ChatRowHorizontalPadding),
            )
        } else {
            Text(
                text = "Edit",
                style = MaterialTheme.typography.titleMedium,
                color = ActionBlue,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(onClick = onEditClick)
                    .padding(horizontal = Dimens.ChatRowHorizontalPadding),
            )

            Text(
                text = "Chats",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Center),
            )

            IconButton(
                onClick = onComposeClick,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = Dimens.SpacingSm),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "New chat",
                    tint = ActionBlue,
                )
            }
        }
    }
}

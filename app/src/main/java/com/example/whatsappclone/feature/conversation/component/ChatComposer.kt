package com.example.whatsappclone.feature.conversation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.ui.theme.ActionBlue
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.TextSecondary

private val ComposerSurface = Color(0xFFF6F6F6)

@Composable
fun ChatComposer(
    text: String,
    onTextChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val hasText = text.isNotBlank()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(ComposerSurface)
            .imePadding()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {}, modifier = Modifier.size(Dimens.ComposerIconSize)) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Attach",
                tint = ActionBlue,
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        OutlinedTextField(
            value = text,
            onValueChange = onTextChanged,
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 36.dp),
            shape = RoundedCornerShape(22.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE5E5EA),
                focusedBorderColor = Color(0xFFE5E5EA),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.EmojiEmotions,
                    contentDescription = "Emoji",
                    tint = TextSecondary,
                    modifier = Modifier.size(Dimens.ComposerIconSize),
                )
            },
            singleLine = false,
            maxLines = 5,
            keyboardOptions = KeyboardOptions(imeAction = if (hasText) ImeAction.Send else ImeAction.Default),
            keyboardActions = KeyboardActions(onSend = { if (hasText) onSendClick() }),
        )

        Spacer(modifier = Modifier.width(4.dp))

        IconButton(onClick = {}, modifier = Modifier.size(Dimens.ComposerIconSize)) {
            Icon(
                imageVector = Icons.Outlined.CameraAlt,
                contentDescription = "Camera",
                tint = TextSecondary,
            )
        }

        if (hasText) {
            IconButton(onClick = onSendClick, modifier = Modifier.size(Dimens.ComposerIconSize)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = ActionBlue,
                )
            }
        } else {
            IconButton(onClick = {}, modifier = Modifier.size(Dimens.ComposerIconSize)) {
                Icon(
                    imageVector = Icons.Outlined.Mic,
                    contentDescription = "Voice",
                    tint = TextSecondary,
                )
            }
        }
    }
}

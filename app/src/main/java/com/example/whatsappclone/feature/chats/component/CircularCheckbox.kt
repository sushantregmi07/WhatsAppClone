package com.example.whatsappclone.feature.chats.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.ui.theme.ActionBlue
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.DisabledGray

@Composable
fun CircularCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier,
) {
    if (checked) {
        Box(
            modifier = modifier
                .size(Dimens.CheckboxSize)
                .background(ActionBlue, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Selected",
                tint = Color.White,
                modifier = Modifier.size(16.dp),
            )
        }
    } else {
        Box(
            modifier = modifier
                .size(Dimens.CheckboxSize)
                .border(
                    width = Dimens.CheckboxBorderWidth,
                    color = DisabledGray,
                    shape = CircleShape,
                ),
        )
    }
}

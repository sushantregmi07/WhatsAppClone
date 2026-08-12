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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.ui.theme.ActionBlue
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.DisabledGray

@Composable
fun CircularCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier,
) {
    val semanticsModifier = modifier.semantics {
        role = Role.Checkbox
        stateDescription = if (checked) "Selected" else "Not selected"
    }

    if (checked) {
        Box(
            modifier = semanticsModifier
                .size(Dimens.CheckboxSize)
                .background(ActionBlue, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp),
            )
        }
    } else {
        Box(
            modifier = semanticsModifier
                .size(Dimens.CheckboxSize)
                .border(
                    width = Dimens.CheckboxBorderWidth,
                    color = DisabledGray,
                    shape = CircleShape,
                ),
        )
    }
}

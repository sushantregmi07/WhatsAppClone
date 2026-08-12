package com.example.whatsappclone.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.whatsappclone.domain.model.AvatarKey
import com.example.whatsappclone.ui.theme.Dimens

/**
 * Renders a contact avatar from the drawable resource mapped by [avatarKey].
 * Falls back to [AvatarPlaceholder] with initials when the resource is missing.
 */
@Composable
fun AvatarImage(
    avatarKey: AvatarKey,
    displayName: String,
    modifier: Modifier = Modifier,
    size: Dp = Dimens.AvatarSizeChatList,
) {
    val context = LocalContext.current
    val resId = context.resources.getIdentifier(
        avatarKey.drawableName,
        "drawable",
        context.packageName,
    )

    if (resId != 0) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = displayName,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(size)
                .clip(CircleShape),
        )
    } else {
        val initials = displayName.split(" ")
            .take(2)
            .mapNotNull { it.firstOrNull()?.toString() }
            .joinToString("")
        AvatarPlaceholder(
            initials = initials,
            modifier = modifier,
            size = size,
        )
    }
}

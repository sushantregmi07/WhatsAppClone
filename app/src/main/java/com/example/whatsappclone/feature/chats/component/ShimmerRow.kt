package com.example.whatsappclone.feature.chats.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.ui.theme.Dimens

@Composable
private fun shimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing)),
        label = "shimmer_translate",
    )
    return Brush.linearGradient(
        colors = listOf(Color(0xFFE0E0E0), Color(0xFFF5F5F5), Color(0xFFE0E0E0)),
        start = Offset(translateAnim.value - 500f, 0f),
        end = Offset(translateAnim.value, 0f),
    )
}

@Composable
fun ShimmerRow(
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
) {
    val brush = shimmerBrush()

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.ChatRowHorizontalPadding,
                    vertical = Dimens.ChatRowVerticalPadding,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(Dimens.AvatarSizeChatList)
                    .clip(CircleShape)
                    .background(brush),
            )

            Spacer(modifier = Modifier.width(Dimens.SpacingMd))

            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush),
                )
                Spacer(modifier = Modifier.height(Dimens.SpacingSm))
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush),
                )
            }
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(start = Dimens.DividerInsetStart),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    }
}

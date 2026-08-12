package com.example.whatsappclone.feature.chats.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.SwipeArchiveBlue
import com.example.whatsappclone.ui.theme.SwipeMoreGray
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeableRow(
    rowId: String,
    isOpen: Boolean,
    onOpenChanged: (String?) -> Unit,
    onMoreClick: () -> Unit,
    onArchiveClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val revealWidthPx = with(density) { (Dimens.SwipeActionWidth * 2).toPx() }
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isOpen) {
        val target = if (isOpen) -revealWidthPx else 0f
        if (offsetX.value != target) {
            offsetX.animateTo(target, tween(200))
        }
    }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .matchParentSize(),
            horizontalArrangement = Arrangement.End,
        ) {
            Box(
                modifier = Modifier
                    .width(Dimens.SwipeActionWidth)
                    .fillMaxHeight()
                    .background(SwipeMoreGray)
                    .clickable {
                        scope.launch { offsetX.animateTo(0f, tween(200)) }
                        onOpenChanged(null)
                        onMoreClick()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = "More",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp),
                    )
                    Text(text = "More", color = Color.White, fontSize = 12.sp)
                }
            }

            Box(
                modifier = Modifier
                    .width(Dimens.SwipeActionWidth)
                    .fillMaxHeight()
                    .background(SwipeArchiveBlue)
                    .clickable {
                        scope.launch { offsetX.animateTo(0f, tween(200)) }
                        onOpenChanged(null)
                        onArchiveClick()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.Archive,
                        contentDescription = "Archive",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp),
                    )
                    Text(text = "Archive", color = Color.White, fontSize = 12.sp)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .background(Color.White)
                .pointerInput(rowId) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            scope.launch {
                                val threshold = -revealWidthPx / 2
                                if (offsetX.value < threshold) {
                                    offsetX.animateTo(-revealWidthPx, tween(200))
                                    onOpenChanged(rowId)
                                } else {
                                    offsetX.animateTo(0f, tween(200))
                                    if (isOpen) onOpenChanged(null)
                                }
                            }
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newValue = (offsetX.value + dragAmount)
                                    .coerceIn(-revealWidthPx, 0f)
                                offsetX.snapTo(newValue)
                            }
                        },
                    )
                },
        ) {
            content()
        }
    }
}

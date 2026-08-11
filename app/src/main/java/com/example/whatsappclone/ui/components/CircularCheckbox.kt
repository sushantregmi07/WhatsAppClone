package com.example.whatsappclone.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.ui.theme.ActionBlue
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.DisabledGray

/**
 * Circular checkbox used in the Chats edit/multi-select mode.
 * Unselected: gray border circle. Selected: filled blue circle with white checkmark.
 */
@Composable
fun CircularCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val fillColor by animateColorAsState(
        targetValue = if (checked) ActionBlue else Color.Transparent,
        label = "checkboxFill",
    )
    val borderColor by animateColorAsState(
        targetValue = if (checked) ActionBlue else DisabledGray,
        label = "checkboxBorder",
    )

    Canvas(
        modifier = modifier
            .size(Dimens.CheckboxSize)
            .clickable { onCheckedChange(!checked) },
    ) {
        val strokeWidth = Dimens.CheckboxBorderWidth.toPx()
        val radius = (size.minDimension - strokeWidth) / 2f
        val center = Offset(size.width / 2f, size.height / 2f)

        if (checked) {
            drawCircle(fillColor, radius)
            // White checkmark
            val w = size.width
            val h = size.height
            val checkStroke = w * 0.12f
            val start = Offset(w * 0.28f, h * 0.50f)
            val mid = Offset(w * 0.44f, h * 0.66f)
            val end = Offset(w * 0.72f, h * 0.34f)
            drawLine(Color.White, start, mid, checkStroke, StrokeCap.Round)
            drawLine(Color.White, mid, end, checkStroke, StrokeCap.Round)
        } else {
            drawCircle(
                color = borderColor,
                radius = radius,
                style = Stroke(width = strokeWidth),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CircularCheckboxUncheckedPreview() {
    CircularCheckbox(checked = false, onCheckedChange = {})
}

@Preview(showBackground = true)
@Composable
private fun CircularCheckboxCheckedPreview() {
    CircularCheckbox(checked = true, onCheckedChange = {})
}

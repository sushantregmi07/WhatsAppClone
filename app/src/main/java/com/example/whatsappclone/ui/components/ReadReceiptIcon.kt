package com.example.whatsappclone.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.ui.theme.ReadReceiptBlue
import com.example.whatsappclone.ui.theme.DisabledGray

/**
 * WhatsApp-style double-check delivery indicator.
 *
 * [isRead] = true → blue double-check (message read).
 * [isRead] = false → gray double-check (message delivered but unread).
 */
@Composable
fun ReadReceiptIcon(
    isRead: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
) {
    val color = if (isRead) ReadReceiptBlue else DisabledGray

    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = w * 0.12f
        val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Round)

        // First checkmark (left)
        val check1Start = Offset(w * 0.08f, h * 0.52f)
        val check1Mid = Offset(w * 0.32f, h * 0.78f)
        val check1End = Offset(w * 0.68f, h * 0.22f)

        drawLine(color, check1Start, check1Mid, strokeWidth, StrokeCap.Round)
        drawLine(color, check1Mid, check1End, strokeWidth, StrokeCap.Round)

        // Second checkmark (right, offset)
        val offset = w * 0.22f
        val check2Start = Offset(check1Start.x + offset, check1Start.y)
        val check2Mid = Offset(check1Mid.x + offset, check1Mid.y)
        val check2End = Offset(check1End.x + offset, check1End.y)

        drawLine(color, check2Start, check2Mid, strokeWidth, StrokeCap.Round)
        drawLine(color, check2Mid, check2End, strokeWidth, StrokeCap.Round)
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadReceiptReadPreview() {
    ReadReceiptIcon(isRead = true, size = 32.dp)
}

@Preview(showBackground = true)
@Composable
private fun ReadReceiptDeliveredPreview() {
    ReadReceiptIcon(isRead = false, size = 32.dp)
}

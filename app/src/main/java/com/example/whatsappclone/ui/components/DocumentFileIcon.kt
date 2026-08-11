package com.example.whatsappclone.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.ui.theme.DocumentIconBlue

/**
 * Document attachment icon: white page with a folded corner and horizontal blue lines.
 * Matches the WhatsApp document attachment appearance from the reference PNGs.
 */
@Composable
fun DocumentFileIcon(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    pageColor: Color = Color.White,
    lineColor: Color = DocumentIconBlue,
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val cornerFold = w * 0.25f
        val strokeWidth = w * 0.06f
        val radius = w * 0.08f

        // Page body with folded top-right corner
        val pagePath = Path().apply {
            moveTo(radius, 0f)
            lineTo(w - cornerFold, 0f)
            lineTo(w, cornerFold)
            lineTo(w, h - radius)
            quadraticTo(w, h, w - radius, h)
            lineTo(radius, h)
            quadraticTo(0f, h, 0f, h - radius)
            lineTo(0f, radius)
            quadraticTo(0f, 0f, radius, 0f)
            close()
        }
        drawPath(pagePath, pageColor)
        drawPath(pagePath, lineColor.copy(alpha = 0.3f), style = Stroke(strokeWidth * 0.5f))

        // Corner fold triangle
        val foldPath = Path().apply {
            moveTo(w - cornerFold, 0f)
            lineTo(w - cornerFold, cornerFold)
            lineTo(w, cornerFold)
            close()
        }
        drawPath(foldPath, lineColor.copy(alpha = 0.15f))

        // Horizontal lines representing text
        val lineY1 = h * 0.42f
        val lineY2 = h * 0.56f
        val lineY3 = h * 0.70f
        val lineStartX = w * 0.15f
        val lineEndX = w * 0.85f
        val shortLineEndX = w * 0.60f

        drawLine(lineColor, Offset(lineStartX, lineY1), Offset(lineEndX, lineY1), strokeWidth, StrokeCap.Round)
        drawLine(lineColor, Offset(lineStartX, lineY2), Offset(lineEndX, lineY2), strokeWidth, StrokeCap.Round)
        drawLine(lineColor, Offset(lineStartX, lineY3), Offset(shortLineEndX, lineY3), strokeWidth, StrokeCap.Round)
    }
}

@Preview(showBackground = true)
@Composable
private fun DocumentFileIconPreview() {
    DocumentFileIcon(size = 80.dp)
}

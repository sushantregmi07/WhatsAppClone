package com.example.whatsappclone.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = WhatsAppTealGreen,
    onPrimary = SurfaceWhite,
    secondary = WhatsAppGreen,
    onSecondary = SurfaceWhite,
    tertiary = ActionBlue,
    onTertiary = SurfaceWhite,
    background = SurfaceWhite,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceLightGray,
    onSurfaceVariant = TextSecondary,
    error = DestructiveRed,
    onError = SurfaceWhite,
    outline = DividerColor,
    outlineVariant = SystemGray5,
)

@Composable
fun WhatsAppCloneTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content,
    )
}

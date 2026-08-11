package com.example.whatsappclone.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = WhatsAppTealGreen,
    onPrimary = WhatsAppWhite,
    secondary = WhatsAppGreen,
    onSecondary = WhatsAppWhite,
    background = WhatsAppWhite,
    onBackground = WhatsAppTextPrimary,
    surface = WhatsAppWhite,
    onSurface = WhatsAppTextPrimary,
    surfaceVariant = WhatsAppLightGray,
    onSurfaceVariant = WhatsAppTextSecondary,
)

@Composable
fun WhatsAppCloneTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content,
    )
}

package com.example.whatsappclone.feature.conversation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.ui.theme.DateChipBackground
import com.example.whatsappclone.ui.theme.DateChipShape
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.TextPrimary

@Composable
fun DateSeparator(
    dateText: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.SpacingSm),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = dateText,
            fontFamily = FontFamily.Default,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            modifier = Modifier
                .background(DateChipBackground, DateChipShape)
                .padding(
                    horizontal = Dimens.DateChipPaddingHorizontal,
                    vertical = Dimens.DateChipPaddingVertical,
                ),
        )
    }
}

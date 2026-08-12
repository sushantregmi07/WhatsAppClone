package com.example.whatsappclone.feature.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.feature.authorization.component.CountryPickerDialog
import com.example.whatsappclone.ui.theme.ActionBlue
import com.example.whatsappclone.ui.theme.Dimens
import com.example.whatsappclone.ui.theme.DisabledGray
import com.example.whatsappclone.ui.theme.TextSecondary

private val TopBarSurface = Color(0xFFF6F6F6)
private val DividerGray = Color(0xFFE5E5EA)

@Composable
fun PhoneAuthorizationScreen(
    uiState: PhoneAuthorizationUiState,
    onPhoneNumberChanged: (String) -> Unit,
    onCountrySelected: (Country) -> Unit,
    onCountryPickerToggle: (Boolean) -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    if (uiState.showCountryPicker) {
        CountryPickerDialog(
            selectedCountry = uiState.country,
            onCountrySelected = onCountrySelected,
            onDismiss = { onCountryPickerToggle(false) },
        )
    }

    Column(modifier = modifier.fillMaxSize().background(Color.White)) {
        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(TopBarSurface)
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(Dimens.TopBarHeight),
        ) {
            Text(
                text = "Phone number",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Center),
            )

            Text(
                text = "Done",
                style = MaterialTheme.typography.titleMedium,
                color = if (uiState.isValid) ActionBlue else DisabledGray,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(enabled = uiState.isValid, onClick = onDoneClick)
                    .padding(horizontal = Dimens.ChatRowHorizontalPadding),
            )
        }

        HorizontalDivider(thickness = 0.5.dp, color = DividerGray)

        // Instruction text
        Text(
            text = "Please confirm your country code and\nenter your phone number",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.ChatRowHorizontalPadding),
        )

        HorizontalDivider(thickness = 0.5.dp, color = DividerGray)

        // Country selector row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clickable { onCountryPickerToggle(true) }
                .padding(horizontal = Dimens.ChatRowHorizontalPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = uiState.country.name,
                style = MaterialTheme.typography.titleMedium,
                color = ActionBlue,
                modifier = Modifier.weight(1f),
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = TextSecondary,
            )
        }

        HorizontalDivider(thickness = 0.5.dp, color = DividerGray)

        // Phone input row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .padding(horizontal = Dimens.ChatRowHorizontalPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = uiState.country.code,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(40.dp),
            )

            VerticalDivider(
                modifier = Modifier.height(24.dp),
                thickness = 0.5.dp,
                color = DividerGray,
            )

            Spacer(modifier = Modifier.width(Dimens.SpacingSm))

            Box(modifier = Modifier.weight(1f)) {
                if (uiState.phoneNumber.isEmpty()) {
                    Text(
                        text = "phone number",
                        style = MaterialTheme.typography.titleMedium,
                        color = DisabledGray,
                    )
                }
                BasicTextField(
                    value = uiState.phoneNumber,
                    onValueChange = onPhoneNumberChanged,
                    textStyle = MaterialTheme.typography.titleMedium,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    cursorBrush = SolidColor(ActionBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                )
            }
        }

        HorizontalDivider(thickness = 0.5.dp, color = DividerGray)
    }
}

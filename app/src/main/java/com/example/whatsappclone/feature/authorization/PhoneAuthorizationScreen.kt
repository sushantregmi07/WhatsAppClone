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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.feature.authorization.component.CountryPickerDialog
import com.example.whatsappclone.ui.theme.ActionBlue

private val PageBackground = Color(0xFFF6F6F6)
private val TopBarSurface = Color(0xFFF6F6F6)
private val FormSurface = Color.White
private val DoneDisabledColor = Color(0xFFD1D1D6)
private val FormDividerColor = Color(0x4A3C3C43)

private object AuthTextStyles {
    val navTitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = emLetterSpacing(-0.02f),
    )
    val instruction = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = emLetterSpacing(-0.01f),
        color = Color.Black,
    )
    val countryName = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = emLetterSpacing(-0.02f),
        color = ActionBlue,
    )
    val countryCode = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = emLetterSpacing(-0.02f),
        color = Color.Black,
    )
    val phoneInput = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = emLetterSpacing(-0.02f),
        color = Color.Black,
    )
    val placeholder = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = emLetterSpacing(-0.02f),
        color = Color(0xFFC7C7CC),
    )
}

private fun emLetterSpacing(value: Float) = TextUnit(value, TextUnitType.Em)

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

    Column(modifier = modifier.fillMaxSize().background(PageBackground)) {
        AuthorizationTopBar(
            isValid = uiState.isValid,
            onDoneClick = onDoneClick,
        )

        AuthorizationInstruction()

        PhoneNumberForm(
            uiState = uiState,
            onCountryPickerToggle = onCountryPickerToggle,
            onPhoneNumberChanged = onPhoneNumberChanged,
            focusRequester = focusRequester,
        )
    }
}

@Composable
private fun AuthorizationTopBar(
    isValid: Boolean,
    onDoneClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(TopBarSurface)
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(44.dp),
    ) {
        Text(
            text = "Phone number",
            style = AuthTextStyles.navTitle,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center),
        )

        Text(
            text = "Done",
            style = AuthTextStyles.navTitle,
            color = if (isValid) ActionBlue else DoneDisabledColor,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable(enabled = isValid, onClick = onDoneClick)
                .padding(horizontal = 16.dp),
        )
    }
}

@Composable
private fun AuthorizationInstruction() {
    HorizontalDivider(thickness = 0.5.dp, color = FormDividerColor)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Please confirm your country code and enter your phone number",
            style = AuthTextStyles.instruction,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 300.dp),
        )
    }

    HorizontalDivider(thickness = 0.5.dp, color = FormDividerColor)
}

@Composable
private fun PhoneNumberForm(
    uiState: PhoneAuthorizationUiState,
    onCountryPickerToggle: (Boolean) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    focusRequester: FocusRequester,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FormSurface),
    ) {
        CountryRow(
            countryName = uiState.country.name,
            onClick = { onCountryPickerToggle(true) },
        )

        HorizontalDivider(
            thickness = 0.5.dp,
            color = FormDividerColor,
            modifier = Modifier.padding(start = 16.dp),
        )

        PhoneNumberRow(
            countryCode = uiState.country.code,
            phoneNumber = uiState.phoneNumber,
            onPhoneNumberChanged = onPhoneNumberChanged,
            focusRequester = focusRequester,
        )
    }

    HorizontalDivider(thickness = 0.5.dp, color = FormDividerColor)
}

@Composable
private fun CountryRow(
    countryName: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clickable(onClick = onClick)
            .padding(start = 16.dp, end = 17.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = countryName,
            style = AuthTextStyles.countryName,
            modifier = Modifier.weight(1f),
        )

        ChevronRight()
    }
}

@Composable
private fun ChevronRight() {
    Box(
        modifier = Modifier
            .size(width = 9.dp, height = 14.dp)
            .drawBehind {
                val w = size.width
                val h = size.height
                val strokeW = 1.5.dp.toPx()
                drawLine(
                    color = Color(0xFFC7C7CC),
                    start = Offset(strokeW / 2, 0f),
                    end = Offset(w - strokeW / 2, h / 2),
                    strokeWidth = strokeW,
                )
                drawLine(
                    color = Color(0xFFC7C7CC),
                    start = Offset(w - strokeW / 2, h / 2),
                    end = Offset(strokeW / 2, h),
                    strokeWidth = strokeW,
                )
            },
    )
}

@Composable
private fun PhoneNumberRow(
    countryCode: String,
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit,
    focusRequester: FocusRequester,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = countryCode,
            style = AuthTextStyles.countryCode,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .width(0.5.dp)
                .height(28.dp)
                .background(FormDividerColor),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (phoneNumber.isEmpty()) {
                Text(
                    text = "phone number",
                    style = AuthTextStyles.placeholder,
                )
            }
            BasicTextField(
                value = phoneNumber,
                onValueChange = onPhoneNumberChanged,
                textStyle = AuthTextStyles.phoneInput,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                cursorBrush = SolidColor(ActionBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .semantics { contentDescription = "Phone number input" },
            )
        }
    }
}

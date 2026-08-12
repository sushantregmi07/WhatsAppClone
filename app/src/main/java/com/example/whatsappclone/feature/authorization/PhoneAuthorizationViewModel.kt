package com.example.whatsappclone.feature.authorization

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val MIN_DIGITS = 7

@HiltViewModel
class PhoneAuthorizationViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PhoneAuthorizationUiState())
    val uiState: StateFlow<PhoneAuthorizationUiState> = _uiState

    fun onPhoneNumberChanged(input: String) {
        val normalized = normalizePhone(input)
        _uiState.update {
            it.copy(
                phoneNumber = input,
                isValid = normalized.length >= MIN_DIGITS,
            )
        }
    }

    fun onCountrySelected(country: Country) {
        _uiState.update {
            it.copy(
                country = country,
                showCountryPicker = false,
            )
        }
    }

    fun onCountryPickerToggle(show: Boolean) {
        _uiState.update { it.copy(showCountryPicker = show) }
    }

    /**
     * Returns true if submit succeeded (valid input), false otherwise.
     * The caller decides how to navigate on success.
     */
    fun submit(): Boolean {
        val normalized = normalizePhone(_uiState.value.phoneNumber)
        return normalized.length >= MIN_DIGITS
    }

    companion object {
        /** Strips all non-digit characters for validation. */
        fun normalizePhone(input: String): String = input.filter { it.isDigit() }
    }
}

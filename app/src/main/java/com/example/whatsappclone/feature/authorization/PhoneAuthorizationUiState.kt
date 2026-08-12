package com.example.whatsappclone.feature.authorization

data class Country(val name: String, val code: String)

val supportedCountries = listOf(
    Country("United States", "+1"),
    Country("Nepal", "+977"),
    Country("United Kingdom", "+44"),
)

data class PhoneAuthorizationUiState(
    val phoneNumber: String = "",
    val country: Country = supportedCountries.first(),
    val isValid: Boolean = false,
    val showCountryPicker: Boolean = false,
)

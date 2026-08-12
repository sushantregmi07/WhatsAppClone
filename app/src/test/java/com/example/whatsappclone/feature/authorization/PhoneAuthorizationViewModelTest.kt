package com.example.whatsappclone.feature.authorization

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PhoneAuthorizationViewModelTest {

    private lateinit var viewModel: PhoneAuthorizationViewModel

    @Before
    fun setUp() {
        viewModel = PhoneAuthorizationViewModel()
    }

    @Test
    fun `normalization strips non-digit characters`() {
        val result = PhoneAuthorizationViewModel.normalizePhone("(555) 123-4567")
        assertEquals("5551234567", result)
    }

    @Test
    fun `input with fewer than 7 digits keeps isValid false`() {
        viewModel.onPhoneNumberChanged("123456")
        assertFalse(viewModel.uiState.value.isValid)
    }

    @Test
    fun `input with 7 digits sets isValid true`() {
        viewModel.onPhoneNumberChanged("1234567")
        assertTrue(viewModel.uiState.value.isValid)
    }

    @Test
    fun `formatted input with 7 plus digits is valid`() {
        viewModel.onPhoneNumberChanged("(555) 123-4567")
        assertTrue(viewModel.uiState.value.isValid)
    }

    @Test
    fun `changing country updates prefix`() {
        val nepal = supportedCountries.first { it.name == "Nepal" }
        viewModel.onCountrySelected(nepal)

        val state = viewModel.uiState.value
        assertEquals("Nepal", state.country.name)
        assertEquals("+977", state.country.code)
        assertFalse(state.showCountryPicker)
    }

    @Test
    fun `submit with invalid input returns false`() {
        viewModel.onPhoneNumberChanged("123")
        assertFalse(viewModel.submit())
    }

    @Test
    fun `submit with valid input returns true`() {
        viewModel.onPhoneNumberChanged("5551234567")
        assertTrue(viewModel.submit())
    }

    @Test
    fun `default country is United States`() {
        val state = viewModel.uiState.value
        assertEquals("United States", state.country.name)
        assertEquals("+1", state.country.code)
    }

    @Test
    fun `toggling country picker updates showCountryPicker`() {
        viewModel.onCountryPickerToggle(true)
        assertTrue(viewModel.uiState.value.showCountryPicker)

        viewModel.onCountryPickerToggle(false)
        assertFalse(viewModel.uiState.value.showCountryPicker)
    }
}

package com.example.seurtest.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.seurtest.data.model.Country
import com.example.seurtest.data.model.commonCountries

class HomeViewModel : ViewModel() {
    var originCountry by mutableStateOf(commonCountries.first { it.isoCode == "ES" })
        private set
    var destinationCountry by mutableStateOf(commonCountries.first { it.isoCode == "ES" })
        private set
    var originPostalCode by mutableStateOf("")
        private set
    var destinationPostalCode by mutableStateOf("")
        private set

    fun onOriginCountryChanged(country: Country) {
        originCountry = country
    }

    fun onDestinationCountryChanged(country: Country) {
        destinationCountry = country
    }

    fun onOriginPostalCodeChanged(cp: String) {
        val maxLen = if (originCountry.isoCode.equals("ES", ignoreCase = true)) 5 else 10
        val filtered = if (originCountry.isoCode.equals("ES", ignoreCase = true)) {
            cp.filter { it.isDigit() }.take(maxLen)
        } else {
            cp.take(maxLen)
        }
        originPostalCode = filtered
    }

    fun onDestinationPostalCodeChanged(cp: String) {
        val maxLen = if (destinationCountry.isoCode.equals("ES", ignoreCase = true)) 5 else 10
        val filtered = if (destinationCountry.isoCode.equals("ES", ignoreCase = true)) {
            cp.filter { it.isDigit() }.take(maxLen)
        } else {
            cp.take(maxLen)
        }
        destinationPostalCode = filtered
    }

    fun swapOriginAndDestination() {
        val tempCountry = originCountry
        val tempCp = originPostalCode
        originCountry = destinationCountry
        originPostalCode = destinationPostalCode
        destinationCountry = tempCountry
        destinationPostalCode = tempCp
    }

    fun isPostalCodeValid(country: Country, cp: String): Boolean {
        val trimmed = cp.trim()
        return if (country.isoCode.equals("ES", ignoreCase = true)) {
            trimmed.length == 5 && trimmed.all { it.isDigit() }
        } else {
            trimmed.length in 3..10
        }
    }

    val isFormValid: Boolean
        get() = isPostalCodeValid(originCountry, originPostalCode) && isPostalCodeValid(destinationCountry, destinationPostalCode)
}

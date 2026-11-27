package com.example.seurtest.ui.misenvios

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.seurtest.data.model.Country
import com.example.seurtest.data.model.commonCountries

class MisEnviosViewModel : ViewModel() {
    var selectedTab by mutableIntStateOf(1) // 0: Enviados, 1: Recibidos (default Recibidos as in screenshot)
        private set

    var isRecipient by mutableStateOf(true) // true: Soy destinatario, false: Soy remitente
        private set

    var trackingId by mutableStateOf("")
        private set

    var postalCode by mutableStateOf("")
        private set

    var mobileOrEmail by mutableStateOf("")
        private set

    var selectedCountry by mutableStateOf(commonCountries.first { it.isoCode == "ES" })
        private set

    val isFormValid: Boolean
        get() = trackingId.isNotBlank() || postalCode.isNotBlank() || mobileOrEmail.isNotBlank()

    fun onTabSelected(tab: Int) {
        selectedTab = tab
    }

    fun onRoleSelected(recipient: Boolean) {
        isRecipient = recipient
    }

    fun onTrackingIdChanged(value: String) {
        trackingId = value
    }

    fun onPostalCodeChanged(value: String) {
        postalCode = value
    }

    fun onMobileOrEmailChanged(value: String) {
        mobileOrEmail = value
    }

    fun onCountryChanged(country: Country) {
        selectedCountry = country
    }
}

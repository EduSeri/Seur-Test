package com.example.seurtest.ui.register

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.seurtest.data.model.Country
import com.example.seurtest.data.model.User
import com.example.seurtest.data.model.commonCountries
import com.example.seurtest.data.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    var nombre by mutableStateOf("")
        private set
    var apellidos by mutableStateOf("")
        private set
    var dni by mutableStateOf("")
        private set
    var telefono by mutableStateOf("")
        private set
    var correo by mutableStateOf("")
        private set
    var contrasenia by mutableStateOf("")
        private set
    var comunicaComercial by mutableStateOf(false)
        private set
    var condicionesServicio by mutableStateOf(false)
        private set
    var contraseniaVisible by mutableStateOf(false)
        private set

    var selectedCountry by mutableStateOf(commonCountries.first { it.isoCode == "ES" })
        private set
    var selectedPrefix by mutableStateOf(commonCountries.first { it.isoCode == "ES" })
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    fun onNombreChanged(value: String) { nombre = value; errorMessage = null }
    fun onApellidosChanged(value: String) { apellidos = value; errorMessage = null }
    fun onDniChanged(value: String) { dni = value; errorMessage = null }
    fun onTelefonoChanged(value: String) { telefono = value; errorMessage = null }
    fun onCorreoChanged(value: String) { correo = value; errorMessage = null }
    fun onContraseniaChanged(value: String) { contrasenia = value; errorMessage = null }
    fun onComunicaComercialChanged(value: Boolean) { comunicaComercial = value }
    fun onCondicionesServicioChanged(value: Boolean) { condicionesServicio = value; errorMessage = null }
    fun toggleContraseniaVisibility() { contraseniaVisible = !contraseniaVisible }

    fun onCountryChanged(country: Country) { selectedCountry = country }
    fun onPrefixChanged(country: Country) { selectedPrefix = country }

    private fun validatePassword(password: String): Boolean {
        if (password.length < 8) return false
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        return hasUppercase && hasLowercase && hasDigit
    }

    private fun validateForm(): Boolean {
        if (nombre.isBlank() || apellidos.isBlank() || dni.isBlank() || telefono.isBlank() || correo.isBlank() || contrasenia.isBlank()) {
            errorMessage = "Por favor, completa todos los campos obligatorios"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            errorMessage = "El formato del correo electrónico no es válido"
            return false
        }
        if (!validatePassword(contrasenia)) {
            errorMessage = "La contraseña debe contener mayúsculas, minúsculas, números y una longitud mínima de 8 caracteres."
            return false
        }
        if (!condicionesServicio) {
            errorMessage = "Debes aceptar los términos y condiciones para continuar"
            return false
        }
        if (userRepository.userExists(correo)) {
            errorMessage = "Ya existe un usuario registrado con este correo electrónico"
            return false
        }
        return true
    }

    fun register(onSuccess: () -> Unit) {
        if (isLoading) return
        if (!validateForm()) return

        viewModelScope.launch {
            isLoading = true
            try {
                delay(1000)
                val newUser = User(
                    nombre = nombre.trim(),
                    apellidos = apellidos.trim(),
                    dni = dni.trim(),
                    telefono = "${selectedPrefix.phonePrefix} ${telefono.trim()}",
                    correo = correo.trim(),
                    contrasenia = contrasenia,
                    comunicaComercial = comunicaComercial,
                    promociones = comunicaComercial,
                    condicionesServicio = condicionesServicio
                )
                val saved = userRepository.saveUser(newUser)
                if (saved) {
                    onSuccess()
                } else {
                    errorMessage = "Ya existe un usuario registrado con este correo electrónico"
                }
            } finally {
                isLoading = false
            }
        }
    }
}

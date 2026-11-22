package com.example.seurtest.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun onEmailChanged(newEmail: String) {
        email = newEmail
        errorMessage = null
    }

    fun onPasswordChanged(newPassword: String) {
        password = newPassword
        errorMessage = null
    }

    fun login(onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Por favor, introduce correo y contraseña"
            return
        }
        isLoading = true
        if (email == "admin@seur.com" && password == "123456") {
            isLoading = false
            onSuccess()
        } else {
            isLoading = false
            errorMessage = "Credenciales incorrectas (Prueba admin@seur.com / 123456)"
        }
    }
}

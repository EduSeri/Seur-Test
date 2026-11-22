package com.example.seurtest.ui.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var rememberMe by mutableStateOf(false)
        private set

    var passwordVisible by mutableStateOf(false)
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

    fun onRememberMeChanged(checked: Boolean) {
        rememberMe = checked
    }

    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun login(onSuccess: () -> Unit) {
        if (email.isBlank()) {
            errorMessage = "El correo electrónico no puede estar vacío"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = "El formato del correo electrónico no es válido"
            return
        }
        if (password.isBlank()) {
            errorMessage = "La contraseña no puede estar vacía"
            return
        }
        if (password.length < 6) {
            errorMessage = "La contraseña debe tener al menos 6 caracteres"
            return
        }

        isLoading = true
        try {
            onSuccess()
        } catch (e: Exception) {
            errorMessage = "Error al iniciar sesión: ${e.localizedMessage}"
        } finally {
            isLoading = false
        }
    }
}

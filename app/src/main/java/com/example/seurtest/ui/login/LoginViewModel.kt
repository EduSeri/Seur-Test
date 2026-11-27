package com.example.seurtest.ui.login

import android.app.Application
import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.seurtest.data.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = application.getSharedPreferences("seur_login_prefs", Context.MODE_PRIVATE)
    private val userRepository = UserRepository(application)

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

    init {
        val savedRememberMe = prefs.getBoolean("KEY_REMEMBER_ME", false)
        rememberMe = savedRememberMe
        if (savedRememberMe) {
            email = prefs.getString("KEY_SAVED_EMAIL", "") ?: ""
            password = prefs.getString("KEY_SAVED_PASSWORD", "") ?: ""
        }
    }

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

    private fun validateCredentials(): Boolean {
        if (email.isBlank()) {
            errorMessage = "El correo electrónico no puede estar vacío"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = "El formato del correo electrónico no es válido"
            return false
        }
        if (password.isBlank()) {
            errorMessage = "La contraseña no puede estar vacía"
            return false
        }
        return true
    }

    private suspend fun simulateBackendAuthentication() {
        delay(1000)
    }

    fun login(onSuccess: () -> Unit) {
        if (isLoading) return
        if (!validateCredentials()) return

        viewModelScope.launch {
            isLoading = true
            try {
                simulateBackendAuthentication()

                val authenticatedUser = userRepository.authenticate(email, password)
                if (authenticatedUser == null) {
                    errorMessage = "Correo o contraseña incorrectos"
                    return@launch
                }

                // Save or clear credentials based on "Recuérdame" checkbox (saving both email and password)
                with(prefs.edit()) {
                    putBoolean("KEY_REMEMBER_ME", rememberMe)
                    if (rememberMe) {
                        putString("KEY_SAVED_EMAIL", email)
                        putString("KEY_SAVED_PASSWORD", password)
                    } else {
                        remove("KEY_SAVED_EMAIL")
                        remove("KEY_SAVED_PASSWORD")
                    }
                    apply()
                }

                onSuccess()
            } finally {
                isLoading = false
            }
        }
    }
}

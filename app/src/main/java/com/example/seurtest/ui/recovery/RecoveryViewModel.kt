package com.example.seurtest.ui.recovery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RecoveryViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var message by mutableStateOf<String?>(null)
        private set

    var isError by mutableStateOf(false)
        private set

    var isSuccess by mutableStateOf(false)
        private set

    fun onEmailChanged(newEmail: String) {
        email = newEmail
        message = null
        isError = false
    }

    fun recoverPassword() {
        if (email.isBlank()) {
            message = "Introduce tu correo electrónico"
            isError = true
            return
        }
        isSuccess = true
        message = "Se han enviado las instrucciones a $email"
        isError = false
    }
}

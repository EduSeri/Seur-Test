package com.example.seurtest.ui.recovery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seurtest.ui.components.SeurScreenLayout
import com.example.seurtest.ui.theme.SeurTestTheme

@Composable
fun RecoveryScreen(
    viewModel: RecoveryViewModel,
    onBackToLogin: () -> Unit,
    onOpenMenu: () -> Unit
) {
    RecoveryContent(
        email = viewModel.email,
        onEmailChanged = viewModel::onEmailChanged,
        isSuccess = viewModel.isSuccess,
        isError = viewModel.isError,
        message = viewModel.message,
        onRecoverClick = viewModel::recoverPassword,
        onBackToLogin = onBackToLogin,
        onOpenMenu = onOpenMenu
    )
}

@Composable
fun RecoveryContent(
    email: String,
    onEmailChanged: (String) -> Unit,
    isSuccess: Boolean,
    isError: Boolean,
    message: String?,
    onRecoverClick: () -> Unit,
    onBackToLogin: () -> Unit,
    onOpenMenu: () -> Unit
) {
    val scrollState = rememberScrollState()

    SeurScreenLayout(
        scrollState = scrollState,
        onOpenMenu = onOpenMenu,
        headerContent = {
            Text(
                text = "Recuperar Contraseña",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        },
        cardContent = {
            if (isSuccess) {
                Text(
                    text = message ?: "¡Correo enviado con éxito!",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                Text(
                    text = "Introduce tu correo electrónico y te enviaremos los pasos para restablecer tu contraseña.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChanged,
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface
                    )
                )

                if (message != null) {
                    Text(
                        text = message,
                        color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Button(
                    onClick = onRecoverClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Enviar instrucciones")
                }
            }

            TextButton(
                onClick = onBackToLogin,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Volver al inicio de sesión", color = MaterialTheme.colorScheme.primary)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RecoveryScreenPreview() {
    SeurTestTheme {
        RecoveryContent(
            email = "",
            onEmailChanged = {},
            isSuccess = false,
            isError = false,
            message = null,
            onRecoverClick = {},
            onBackToLogin = {},
            onOpenMenu = {}
        )
    }
}

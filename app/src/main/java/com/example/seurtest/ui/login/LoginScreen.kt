package com.example.seurtest.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val SeurBlue = Color(0xFF00539F)
val SeurRed = Color(0xFFE41E26)
val SeurLightGrayBackground = Color(0xFFF4F5F7)

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRecovery: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SeurBlue,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Top-left image placeholder
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🖼️", fontSize = 16.sp)
                }

                // Top-right buttons (non-functional placeholders)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { /* Non-functional */ }, modifier = Modifier.size(36.dp)) {
                        Text("🔍", fontSize = 18.sp)
                    }
                    TextButton(onClick = { /* Non-functional */ }, modifier = Modifier.size(36.dp)) {
                        Text("🔔", fontSize = 18.sp)
                    }
                    TextButton(onClick = { /* Non-functional */ }, modifier = Modifier.size(36.dp)) {
                        Text("👤", fontSize = 18.sp)
                    }
                    TextButton(onClick = { /* Non-functional */ }, modifier = Modifier.size(36.dp)) {
                        Text("☰", fontSize = 18.sp)
                    }
                }
            }

            // Header Texts
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "miSEUR",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Te damos la\nbienvenida",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 34.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Inicia sesión para gestionar tus envíos.",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bottom White Card Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .navigationBarsPadding(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Email field
                        Text(
                            text = "Email o usuario",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = viewModel.email,
                            onValueChange = viewModel::onEmailChanged,
                            placeholder = { Text("nombre@email.com", color = Color.Gray) },
                            leadingIcon = { Text("✉️", fontSize = 18.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray,
                                focusedBorderColor = SeurBlue,
                                unfocusedContainerColor = SeurLightGrayBackground,
                                focusedContainerColor = SeurLightGrayBackground
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Password field
                        Text(
                            text = "Contraseña",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = viewModel.password,
                            onValueChange = viewModel::onPasswordChanged,
                            placeholder = { Text("••••••••", color = Color.Gray) },
                            leadingIcon = { Text("🔒", fontSize = 18.sp) },
                            trailingIcon = {
                                TextButton(onClick = { viewModel.togglePasswordVisibility() }) {
                                    Text(if (viewModel.passwordVisible) "Ocultar" else "Mostrar", color = SeurBlue, fontSize = 12.sp)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray,
                                focusedBorderColor = SeurBlue,
                                unfocusedContainerColor = SeurLightGrayBackground,
                                focusedContainerColor = SeurLightGrayBackground
                            )
                        )

                        if (viewModel.errorMessage != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = viewModel.errorMessage!!,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Remember me & Recovery
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = viewModel.rememberMe,
                                    onCheckedChange = viewModel::onRememberMeChanged,
                                    colors = CheckboxDefaults.colors(checkedColor = SeurBlue)
                                )
                                Text("Seguir conectado", fontSize = 13.sp, color = Color.DarkGray)
                            }

                            TextButton(onClick = onNavigateToRecovery) {
                                Text("Recuperar acceso", color = SeurBlue, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Login Button (Seur Red/Orange)
                        Button(
                            onClick = { viewModel.login(onLoginSuccess) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SeurRed),
                            enabled = !viewModel.isLoading
                        ) {
                            if (viewModel.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White
                                )
                            } else {
                                Text("Acceder", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Divider "o continúa con"
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                            Text(
                                text = " o continúa con ",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Reserved social login space (dashed border)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Espacio reservado · acceso con redes sociales",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    // Bottom register footer
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "¿Eres nuevo en miSEUR?",
                            fontSize = 13.sp,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = { /* Non-functional registration */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            border = BorderStroke(1.dp, SeurRed)
                        ) {
                            Text("Crear cuenta", color = SeurRed, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

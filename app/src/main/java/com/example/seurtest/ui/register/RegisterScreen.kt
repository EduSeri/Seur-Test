package com.example.seurtest.ui.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seurtest.R
import com.example.seurtest.data.model.Country
import com.example.seurtest.data.model.commonCountries
import com.example.seurtest.ui.components.CountryDropdown
import com.example.seurtest.ui.components.PrefixDropdown
import com.example.seurtest.ui.components.SeurScreenLayout
import com.example.seurtest.ui.theme.SeurTestTheme

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToRecovery: () -> Unit
) {
    RegisterContent(
        nombre = viewModel.nombre,
        onNombreChanged = viewModel::onNombreChanged,
        apellidos = viewModel.apellidos,
        onApellidosChanged = viewModel::onApellidosChanged,
        dni = viewModel.dni,
        onDniChanged = viewModel::onDniChanged,
        telefono = viewModel.telefono,
        onTelefonoChanged = viewModel::onTelefonoChanged,
        correo = viewModel.correo,
        onCorreoChanged = viewModel::onCorreoChanged,
        contrasenia = viewModel.contrasenia,
        onContraseniaChanged = viewModel::onContraseniaChanged,
        comunicaComercial = viewModel.comunicaComercial,
        onComunicaComercialChanged = viewModel::onComunicaComercialChanged,
        condicionesServicio = viewModel.condicionesServicio,
        onCondicionesServicioChanged = viewModel::onCondicionesServicioChanged,
        contraseniaVisible = viewModel.contraseniaVisible,
        onToggleContraseniaVisibility = viewModel::toggleContraseniaVisibility,
        selectedCountry = viewModel.selectedCountry,
        onCountryChanged = viewModel::onCountryChanged,
        selectedPrefix = viewModel.selectedPrefix,
        onPrefixChanged = viewModel::onPrefixChanged,
        errorMessage = viewModel.errorMessage,
        isLoading = viewModel.isLoading,
        onRegisterClick = { viewModel.register(onRegisterSuccess) },
        onNavigateToLogin = onNavigateToLogin,
        onNavigateToRecovery = onNavigateToRecovery
    )
}

@Composable
fun RegisterContent(
    nombre: String,
    onNombreChanged: (String) -> Unit,
    apellidos: String,
    onApellidosChanged: (String) -> Unit,
    dni: String,
    onDniChanged: (String) -> Unit,
    telefono: String,
    onTelefonoChanged: (String) -> Unit,
    correo: String,
    onCorreoChanged: (String) -> Unit,
    contrasenia: String,
    onContraseniaChanged: (String) -> Unit,
    comunicaComercial: Boolean,
    onComunicaComercialChanged: (Boolean) -> Unit,
    condicionesServicio: Boolean,
    onCondicionesServicioChanged: (Boolean) -> Unit,
    contraseniaVisible: Boolean,
    onToggleContraseniaVisibility: () -> Unit,
    selectedCountry: Country,
    onCountryChanged: (Country) -> Unit,
    selectedPrefix: Country,
    onPrefixChanged: (Country) -> Unit,
    errorMessage: String?,
    isLoading: Boolean,
    onRegisterClick: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToRecovery: () -> Unit
) {
    val scrollState = rememberScrollState()

    SeurScreenLayout(
        scrollState = scrollState,
        showActions = false,
        headerContent = {
            Text(
                text = stringResource(R.string.mi_seur),
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.create_account_title),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 34.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.create_account_subtitle),
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
        },
        cardContent = {
            // Datos personales section
            Text(
                text = stringResource(R.string.personal_data),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = onNombreChanged,
                label = { Text(stringResource(R.string.name_label)) },
                placeholder = { Text(stringResource(R.string.name_placeholder)) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            // Apellidos
            OutlinedTextField(
                value = apellidos,
                onValueChange = onApellidosChanged,
                label = { Text(stringResource(R.string.surname_label)) },
                placeholder = { Text(stringResource(R.string.surname_placeholder)) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            // Country selector dropdown (preselected to Spain)
            CountryDropdown(
                selectedCountry = selectedCountry,
                onCountrySelected = onCountryChanged,
                label = stringResource(R.string.country_label)
            )

            // DNI / NIF / NIE
            OutlinedTextField(
                value = dni,
                onValueChange = onDniChanged,
                label = { Text(stringResource(R.string.nif_label)) },
                placeholder = { Text(stringResource(R.string.nif_placeholder)) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            // Contacto section
            Text(
                text = stringResource(R.string.contact_section),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Prefijo y Móvil in Row (properly aligned)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PrefixDropdown(
                    selectedCountry = selectedPrefix,
                    onCountrySelected = onPrefixChanged,
                    modifier = Modifier.width(130.dp),
                    label = "Prefijo"
                )

                // Móvil field
                OutlinedTextField(
                    value = telefono,
                    onValueChange = onTelefonoChanged,
                    label = { Text(stringResource(R.string.phone_label)) },
                    placeholder = { Text(stringResource(R.string.phone_placeholder)) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors()
                )
            }

            // Email
            OutlinedTextField(
                value = correo,
                onValueChange = onCorreoChanged,
                label = { Text(stringResource(R.string.email_label)) },
                placeholder = { Text(stringResource(R.string.email_placeholder)) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            // Acceso section
            Text(
                text = stringResource(R.string.access_section),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Contraseña
            OutlinedTextField(
                value = contrasenia,
                onValueChange = onContraseniaChanged,
                label = { Text(stringResource(R.string.password_label)) },
                placeholder = { Text(stringResource(R.string.password_placeholder)) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (contraseniaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = onToggleContraseniaVisibility) {
                        Icon(
                            imageVector = if (contraseniaVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            // Helper text below password as requested
            Text(
                text = stringResource(R.string.password_requirement_text),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )

            // Checkbox 1: Promociones / Comunicaciones comerciales (No obligatorio)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Checkbox(
                    checked = comunicaComercial,
                    onCheckedChange = onComunicaComercialChanged,
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.promociones_checkbox),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 11.dp)
                )
            }

            // Checkbox 2: Términos y condiciones (Obligatorio)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Checkbox(
                    checked = condicionesServicio,
                    onCheckedChange = onCondicionesServicioChanged,
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.terms_checkbox),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 11.dp)
                )
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Registrarse Button
            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = stringResource(R.string.register_button),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            // Footer: ¿Ya estás registrado?
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.already_registered),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                OutlinedButton(
                    onClick = onNavigateToLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
                ) {
                    Text(
                        text = stringResource(R.string.login_link),
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                TextButton(onClick = onNavigateToRecovery) {
                    Text(
                        text = stringResource(R.string.recover_password_link),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    )
}

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
    focusedTextColor = MaterialTheme.colorScheme.onSurface
)

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    SeurTestTheme {
        RegisterContent(
            nombre = "",
            onNombreChanged = {},
            apellidos = "",
            onApellidosChanged = {},
            dni = "",
            onDniChanged = {},
            telefono = "",
            onTelefonoChanged = {},
            correo = "",
            onCorreoChanged = {},
            contrasenia = "",
            onContraseniaChanged = {},
            comunicaComercial = false,
            onComunicaComercialChanged = {},
            condicionesServicio = false,
            onCondicionesServicioChanged = {},
            contraseniaVisible = false,
            onToggleContraseniaVisibility = {},
            selectedCountry = commonCountries.first { it.isoCode == "ES" },
            onCountryChanged = {},
            selectedPrefix = commonCountries.first { it.isoCode == "ES" },
            onPrefixChanged = {},
            errorMessage = null,
            isLoading = false,
            onRegisterClick = {},
            onNavigateToLogin = {},
            onNavigateToRecovery = {}
        )
    }
}

package com.example.seurtest.ui.misenvios

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seurtest.R
import com.example.seurtest.data.model.Country
import com.example.seurtest.data.model.commonCountries
import com.example.seurtest.ui.components.CountryDropdown
import com.example.seurtest.ui.components.SeurScreenLayout
import com.example.seurtest.ui.theme.SeurTestTheme

@Composable
fun MisEnviosScreen(
    viewModel: MisEnviosViewModel,
    onBack: () -> Unit,
    onOpenMenu: () -> Unit
) {
    MisEnviosContent(
        selectedTab = viewModel.selectedTab,
        onTabSelected = viewModel::onTabSelected,
        isRecipient = viewModel.isRecipient,
        onRoleSelected = viewModel::onRoleSelected,
        trackingId = viewModel.trackingId,
        onTrackingIdChanged = viewModel::onTrackingIdChanged,
        postalCode = viewModel.postalCode,
        onPostalCodeChanged = viewModel::onPostalCodeChanged,
        mobileOrEmail = viewModel.mobileOrEmail,
        onMobileOrEmailChanged = viewModel::onMobileOrEmailChanged,
        selectedCountry = viewModel.selectedCountry,
        onCountryChanged = viewModel::onCountryChanged,
        isFormValid = viewModel.isFormValid,
        onBack = onBack,
        onOpenMenu = onOpenMenu
    )
}

@Composable
fun MisEnviosContent(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    isRecipient: Boolean,
    onRoleSelected: (Boolean) -> Unit,
    trackingId: String,
    onTrackingIdChanged: (String) -> Unit,
    postalCode: String,
    onPostalCodeChanged: (String) -> Unit,
    mobileOrEmail: String,
    onMobileOrEmailChanged: (String) -> Unit,
    selectedCountry: Country,
    onCountryChanged: (Country) -> Unit,
    isFormValid: Boolean,
    onBack: () -> Unit,
    onOpenMenu: () -> Unit
) {
    val scrollState = rememberScrollState()

    SeurScreenLayout(
        scrollState = scrollState,
        onOpenMenu = onOpenMenu,
        headerContent = null,
        cardContent = {
            // Title: Mis envíos
            Text(
                text = stringResource(R.string.mis_envios),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Tabs: Enviados (0) vs Recibidos (0)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                TabButton(
                    text = stringResource(R.string.enviados_count),
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )
                Spacer(modifier = Modifier.width(32.dp))
                TabButton(
                    text = stringResource(R.string.recibidos_count),
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )
            }

            // Ad Banner / Publicidad placeholder
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.ad_banner_placeholder),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Tracking ID field
            OutlinedTextField(
                value = trackingId,
                onValueChange = onTrackingIdChanged,
                placeholder = { Text(stringResource(R.string.tracking_id_placeholder), color = MaterialTheme.colorScheme.onSurfaceVariant) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors()
            )

            // Role selector buttons: Soy destinatario / Soy remitente
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { onRoleSelected(true) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        width = if (isRecipient) 2.dp else 1.dp,
                        color = if (isRecipient) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isRecipient) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else Color.Transparent
                    )
                ) {
                    Text(
                        text = stringResource(R.string.soy_destinatario),
                        color = if (isRecipient) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        fontWeight = if (isRecipient) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }

                OutlinedButton(
                    onClick = { onRoleSelected(false) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        width = if (!isRecipient) 2.dp else 1.dp,
                        color = if (!isRecipient) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (!isRecipient) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else Color.Transparent
                    )
                ) {
                    Text(
                        text = stringResource(R.string.soy_remitente),
                        color = if (!isRecipient) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        fontWeight = if (!isRecipient) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            }

            // Country & Destination Postal Code container
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CountryDropdown(
                        selectedCountry = selectedCountry,
                        onCountrySelected = onCountryChanged,
                        label = stringResource(R.string.country_label)
                    )

                    OutlinedTextField(
                        value = postalCode,
                        onValueChange = onPostalCodeChanged,
                        placeholder = { Text(stringResource(R.string.postal_code_placeholder), color = MaterialTheme.colorScheme.onSurfaceVariant) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }

            // Mobile or Email destination field
            OutlinedTextField(
                value = mobileOrEmail,
                onValueChange = onMobileOrEmailChanged,
                placeholder = { Text(stringResource(R.string.mobile_email_placeholder), color = MaterialTheme.colorScheme.onSurfaceVariant) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = textFieldColors()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Ver detalle Button
            Button(
                onClick = { /* Future detail navigation */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFormValid) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                enabled = isFormValid
            ) {
                Text(
                    text = stringResource(R.string.view_detail_button),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isFormValid) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Cancelar Button (navigates back to Home)
            TextButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.cancel_button),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer with ~6 lines of Lorem Ipsum
            SeurFooter()
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

@Composable
fun SeurFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(modifier = Modifier.padding(bottom = 16.dp), color = MaterialTheme.colorScheme.outlineVariant)
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(3.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(1.5.dp))
            )
        } else {
            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MisEnviosScreenPreview() {
    SeurTestTheme {
        MisEnviosContent(
            selectedTab = 1,
            onTabSelected = {},
            isRecipient = true,
            onRoleSelected = {},
            trackingId = "",
            onTrackingIdChanged = {},
            postalCode = "",
            onPostalCodeChanged = {},
            mobileOrEmail = "",
            onMobileOrEmailChanged = {},
            selectedCountry = commonCountries.first { it.isoCode == "ES" },
            onCountryChanged = {},
            isFormValid = false,
            onBack = {},
            onOpenMenu = {}
        )
    }
}

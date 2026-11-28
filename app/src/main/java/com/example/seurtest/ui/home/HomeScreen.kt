package com.example.seurtest.ui.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.seurtest.ui.misenvios.SeurFooter
import com.example.seurtest.ui.theme.SeurTestTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToMisEnvios: () -> Unit,
    onOpenMenu: () -> Unit,
    onStartShipment: (OriginDestination) -> Unit = {}
) {
    val context = LocalContext.current

    HomeContent(
        originCountry = viewModel.originCountry,
        onOriginCountryChanged = viewModel::onOriginCountryChanged,
        destinationCountry = viewModel.destinationCountry,
        onDestinationCountryChanged = viewModel::onDestinationCountryChanged,
        originPostalCode = viewModel.originPostalCode,
        onOriginPostalCodeChanged = viewModel::onOriginPostalCodeChanged,
        destinationPostalCode = viewModel.destinationPostalCode,
        onDestinationPostalCodeChanged = viewModel::onDestinationPostalCodeChanged,
        onSwap = viewModel::swapOriginAndDestination,
        isFormValid = viewModel.isFormValid,
        onNavigateToMisEnvios = onNavigateToMisEnvios,
        onOpenMenu = onOpenMenu,
        onStartShipment = { origin, dest ->
            Toast.makeText(context, "Próximamente", Toast.LENGTH_SHORT).show()
            onStartShipment(OriginDestination(origin, viewModel.originPostalCode, dest, viewModel.destinationPostalCode))
        }
    )
}

data class OriginDestination(
    val originCountry: Country,
    val originCp: String,
    val destCountry: Country,
    val destCp: String
)

@Composable
fun HomeContent(
    originCountry: Country,
    onOriginCountryChanged: (Country) -> Unit,
    destinationCountry: Country,
    onDestinationCountryChanged: (Country) -> Unit,
    originPostalCode: String,
    onOriginPostalCodeChanged: (String) -> Unit,
    destinationPostalCode: String,
    onDestinationPostalCodeChanged: (String) -> Unit,
    onSwap: () -> Unit,
    isFormValid: Boolean,
    onNavigateToMisEnvios: () -> Unit,
    onOpenMenu: () -> Unit,
    onStartShipment: (Country, Country) -> Unit
) {
    val scrollState = rememberScrollState()

    SeurScreenLayout(
        scrollState = scrollState,
        onOpenMenu = onOpenMenu,
        headerContent = {
            // Carousel in the orange section between topbar and surface card
            HomeCarouselSection()
        },
        cardContent = {
            // Section Title: Haz tu envío
            Text(
                text = stringResource(R.string.haz_tu_envio),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Shipment Form Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // ORIGEN BLOCK
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.origen),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CountryDropdown(
                                selectedCountry = originCountry,
                                onCountrySelected = onOriginCountryChanged,
                                modifier = Modifier.weight(0.45f),
                                label = "País"
                            )

                            OutlinedTextField(
                                value = originPostalCode,
                                onValueChange = onOriginPostalCodeChanged,
                                label = { Text(stringResource(R.string.postal_code_input_label)) },
                                singleLine = true,
                                shape = RoundedCornerShape(14.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = if (originCountry.isoCode.equals("ES", ignoreCase = true)) KeyboardType.Number else KeyboardType.Text
                                ),
                                modifier = Modifier.weight(1f),
                                colors = shipmentTextFieldColors()
                            )
                        }
                    }

                    // Divider and Swap Button Row
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        IconButton(
                            onClick = onSwap,
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.surface, CircleShape)
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SwapVert,
                                contentDescription = stringResource(R.string.swap_origin_destination),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    // DESTINO BLOCK
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.destino),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CountryDropdown(
                                selectedCountry = destinationCountry,
                                onCountrySelected = onDestinationCountryChanged,
                                modifier = Modifier.weight(0.45f),
                                label = "País"
                            )

                            OutlinedTextField(
                                value = destinationPostalCode,
                                onValueChange = onDestinationPostalCodeChanged,
                                label = { Text(stringResource(R.string.postal_code_input_label)) },
                                singleLine = true,
                                shape = RoundedCornerShape(14.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = if (destinationCountry.isoCode.equals("ES", ignoreCase = true)) KeyboardType.Number else KeyboardType.Text
                                ),
                                modifier = Modifier.weight(1f),
                                colors = shipmentTextFieldColors()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Continuar Button
                    Button(
                        onClick = { onStartShipment(originCountry, destinationCountry) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        enabled = isFormValid
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.continuar),
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isFormValid) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = if (isFormValid) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mis envíos Button at the bottom
            OutlinedButton(
                onClick = onNavigateToMisEnvios,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Inventory2,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = stringResource(R.string.mis_envios),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SeurFooter()
        }
    )
}

@Composable
private fun shipmentTextFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    focusedContainerColor = MaterialTheme.colorScheme.surface,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
    focusedTextColor = MaterialTheme.colorScheme.onSurface
)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SeurTestTheme {
        HomeContent(
            originCountry = commonCountries.first { it.isoCode == "ES" },
            onOriginCountryChanged = {},
            destinationCountry = commonCountries.first { it.isoCode == "ES" },
            onDestinationCountryChanged = {},
            originPostalCode = "28001",
            onOriginPostalCodeChanged = {},
            destinationPostalCode = "08001",
            onDestinationPostalCodeChanged = {},
            onSwap = {},
            isFormValid = true,
            onNavigateToMisEnvios = {},
            onOpenMenu = {},
            onStartShipment = { _, _ -> }
        )
    }
}

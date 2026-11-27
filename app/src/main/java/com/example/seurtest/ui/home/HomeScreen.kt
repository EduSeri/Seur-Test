package com.example.seurtest.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seurtest.R
import com.example.seurtest.ui.components.SeurScreenLayout
import com.example.seurtest.ui.misenvios.SeurFooter
import com.example.seurtest.ui.theme.SeurTestTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onLogout: () -> Unit,
    onNavigateToMisEnvios: () -> Unit,
    onOpenMenu: () -> Unit
) {
    HomeContent(
        onLogout = onLogout,
        onNavigateToMisEnvios = onNavigateToMisEnvios,
        onOpenMenu = onOpenMenu
    )
}

@Composable
fun HomeContent(
    onLogout: () -> Unit,
    onNavigateToMisEnvios: () -> Unit,
    onOpenMenu: () -> Unit
) {
    val scrollState = rememberScrollState()

    SeurScreenLayout(
        scrollState = scrollState,
        onOpenMenu = onOpenMenu,
        headerContent = null,
        cardContent = {
            // Welcome Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.home_title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(R.string.home_subtitle),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mis envíos navigation button
            Button(
                onClick = onNavigateToMisEnvios,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = stringResource(R.string.go_to_mis_envios),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Logout button
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(
                    text = "Cerrar Sesión",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer with ~6 lines of Lorem Ipsum
            SeurFooter()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SeurTestTheme {
        HomeContent(
            onLogout = {},
            onNavigateToMisEnvios = {},
            onOpenMenu = {}
        )
    }
}

package com.example.seurtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.seurtest.ui.home.HomeScreen
import com.example.seurtest.ui.login.LoginScreen
import com.example.seurtest.ui.login.LoginViewModel
import com.example.seurtest.ui.navigation.Screen
import com.example.seurtest.ui.recovery.RecoveryScreen
import com.example.seurtest.ui.recovery.RecoveryViewModel
import com.example.seurtest.ui.theme.SeurTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeurTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainAppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainAppNavigation(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    val loginViewModel = remember { LoginViewModel() }
    val recoveryViewModel = remember { RecoveryViewModel() }

    Box(modifier = modifier.fillMaxSize()) {
        when (currentScreen) {
            is Screen.Login -> {
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = { currentScreen = Screen.Home },
                    onNavigateToRecovery = { currentScreen = Screen.Recovery }
                )
            }
            is Screen.Recovery -> {
                RecoveryScreen(
                    viewModel = recoveryViewModel,
                    onBackToLogin = { currentScreen = Screen.Login }
                )
            }
            is Screen.Home -> {
                HomeScreen(
                    onLogout = { currentScreen = Screen.Login }
                )
            }
        }
    }
}

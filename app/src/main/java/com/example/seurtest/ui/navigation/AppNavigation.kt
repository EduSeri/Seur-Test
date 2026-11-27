package com.example.seurtest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seurtest.ui.home.HomeScreen
import com.example.seurtest.ui.home.HomeViewModel
import com.example.seurtest.ui.login.LoginScreen
import com.example.seurtest.ui.login.LoginViewModel
import com.example.seurtest.ui.misenvios.MisEnviosScreen
import com.example.seurtest.ui.misenvios.MisEnviosViewModel
import com.example.seurtest.ui.recovery.RecoveryScreen
import com.example.seurtest.ui.recovery.RecoveryViewModel
import com.example.seurtest.ui.register.RegisterScreen
import com.example.seurtest.ui.register.RegisterViewModel
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Login : Screen

    @Serializable
    data object Register : Screen

    @Serializable
    data object Recovery : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data object MisEnvios : Screen
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login,
        modifier = modifier
    ) {
        composable<Screen.Login> {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                },
                onNavigateToRecovery = {
                    navController.navigate(Screen.Recovery)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register)
                }
            )
        }
        composable<Screen.Register> {
            val viewModel: RegisterViewModel = viewModel()
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    navController.navigate(Screen.Login) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToRecovery = {
                    navController.navigate(Screen.Recovery)
                }
            )
        }
        composable<Screen.Recovery> {
            val viewModel: RecoveryViewModel = viewModel()
            RecoveryScreen(
                viewModel = viewModel,
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable<Screen.Home> {
            val viewModel: HomeViewModel = viewModel()
            HomeScreen(
                viewModel = viewModel,
                onLogout = {
                    navController.navigate(Screen.Login) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                onNavigateToMisEnvios = {
                    navController.navigate(Screen.MisEnvios)
                }
            )
        }
        composable<Screen.MisEnvios> {
            val viewModel: MisEnviosViewModel = viewModel()
            MisEnviosScreen(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

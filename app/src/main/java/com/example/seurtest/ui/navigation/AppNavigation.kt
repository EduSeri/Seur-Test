package com.example.seurtest.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.seurtest.ui.components.SeurSideMenu
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    var menuOpen by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Screen.Login,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<Screen.Login> {
                val viewModel: LoginViewModel = viewModel()
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {
                        menuOpen = false
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
                    },
                    onOpenMenu = {}
                )
            }
            composable<Screen.Home> {
                val viewModel: HomeViewModel = viewModel()
                HomeScreen(
                    viewModel = viewModel,
                    onLogout = {
                        menuOpen = false
                        navController.navigate(Screen.Login) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    onNavigateToMisEnvios = {
                        navController.navigate(Screen.MisEnvios)
                    },
                    onOpenMenu = { menuOpen = true }
                )
            }
            composable<Screen.MisEnvios> {
                val viewModel: MisEnviosViewModel = viewModel()
                MisEnviosScreen(
                    viewModel = viewModel,
                    onBack = {
                        navController.popBackStack()
                    },
                    onOpenMenu = { menuOpen = true }
                )
            }
        }

        SeurSideMenu(
            visible = menuOpen,
            currentRoute = currentRoute,
            onClose = { menuOpen = false },
            onNavigate = { screen ->
                menuOpen = false
                navController.navigate(screen) {
                    if (screen == Screen.Home || screen == Screen.MisEnvios) {
                        launchSingleTop = true
                    }
                }
            },
            onLogout = {
                menuOpen = false
                navController.navigate(Screen.Login) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            },
            onComingSoon = { title ->
                menuOpen = false
                Toast.makeText(context, "$title - Próximamente", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

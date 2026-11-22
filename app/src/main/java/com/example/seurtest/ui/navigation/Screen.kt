package com.example.seurtest.ui.navigation

sealed class Screen {
    object Login : Screen()
    object Recovery : Screen()
    object Home : Screen()
}

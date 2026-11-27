package com.example.seurtest.data.repository

import android.content.Context
import com.example.seurtest.data.model.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserRepository(context: Context) {
    private val prefs = context.getSharedPreferences("seur_user_db_prefs", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }

    init {
        // Initialize default users if not already present
        if (!prefs.contains(USERS_KEY)) {
            val defaultUsers = listOf(
                User(
                    nombre = "Edu",
                    apellidos = "Test",
                    dni = "12345678A",
                    telefono = "600000001",
                    correo = "edu@edu.es",
                    contrasenia = "Password123",
                    comunicaComercial = true,
                    promociones = true,
                    condicionesServicio = true
                ),
                User(
                    nombre = "Admin",
                    apellidos = "Seur",
                    dni = "87654321B",
                    telefono = "600000002",
                    correo = "Admin@admin.es",
                    contrasenia = "AdminPassword1",
                    comunicaComercial = true,
                    promociones = true,
                    condicionesServicio = true
                )
            )
            saveAllUsers(defaultUsers)
        }
    }

    fun getAllUsers(): List<User> {
        val jsonString = prefs.getString(USERS_KEY, null) ?: return emptyList()
        return try {
            json.decodeFromString<List<User>>(jsonString)
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveAllUsers(users: List<User>) {
        val jsonString = json.encodeToString(users)
        prefs.edit().putString(USERS_KEY, jsonString).apply()
    }

    fun userExists(correo: String): Boolean {
        return getAllUsers().any { it.correo.equals(correo, ignoreCase = true) }
    }

    fun saveUser(user: User): Boolean {
        if (userExists(user.correo)) return false
        val currentUsers = getAllUsers().toMutableList()
        currentUsers.add(user)
        saveAllUsers(currentUsers)
        return true
    }

    fun authenticate(correo: String, contrasenia: String): User? {
        return getAllUsers().find {
            it.correo.equals(correo, ignoreCase = true) && it.contrasenia == contrasenia
        }
    }

    companion object {
        private const val USERS_KEY = "KEY_USERS_DB"
    }
}

package com.example.seurtest.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val nombre: String,
    val apellidos: String,
    val dni: String,
    val telefono: String,
    val correo: String,
    val contrasenia: String,
    val comunicaComercial: Boolean,
    val promociones: Boolean,
    val condicionesServicio: Boolean
)

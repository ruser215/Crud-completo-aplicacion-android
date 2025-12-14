package com.example.crudcompleto.clasepojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize //esto es para poder enviar este objeto entre avtivitys
data class Juego(
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val imagen: String,
    val desarrolladora: String,
    val plataforma: String,
    val precio: Double
) : Parcelable

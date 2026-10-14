package com.carbajal.ayudacampus.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carbajal.ayudacampus.model.Rol

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val correo: String,
    val clave: String,
    val rol: Rol // CLIENTE | ADMIN
)

package com.isc.petshopapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="cliente")
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name="cedula")
    val cedula: String?,
    @ColumnInfo(name="nombre")
    val nombre: String?,
    @ColumnInfo(name="apellidos")
    val apellidos: String?,
    @ColumnInfo(name="edad")
    val edad: Int
)
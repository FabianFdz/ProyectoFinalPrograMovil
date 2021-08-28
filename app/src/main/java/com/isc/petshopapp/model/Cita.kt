package com.isc.petshopapp.model

import androidx.room.Entity

data class Cita(
  val id: String? = "",
  val descripcion: String? = "",
  val titulo: String? = "",
  val fecha: String? = "",
  val inicio: String? = "",
  val fin: String? = ""
)

package com.isc.petshopapp.model

import androidx.room.Entity
import com.google.firebase.auth.FirebaseAuth

data class Cita(
  val id: String? = "",
  val descripcion: String? = "",
  val titulo: String? = "",
  val fecha: String? = "",
  val inicio: String? = "",
  val fin: String? = "",
  val email: String? = FirebaseAuth.getInstance().currentUser?.email
)
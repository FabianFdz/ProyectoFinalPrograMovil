package com.isc.petshopapp.model

data class Response(
    var servicios: List<Servicio>? = null,
    var exception: Exception? = null
)

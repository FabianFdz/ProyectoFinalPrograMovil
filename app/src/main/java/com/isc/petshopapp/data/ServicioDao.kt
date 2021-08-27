package com.isc.petshopapp.data

import com.isc.petshopapp.model.Response

interface ServicioDao {
    fun onResponse(response: Response)
}
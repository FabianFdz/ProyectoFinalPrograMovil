package com.isc.petshopapp.repositorio

import androidx.lifecycle.LiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.isc.petshopapp.data.ServicioDao
import com.isc.petshopapp.model.Response
import com.isc.petshopapp.model.Servicio

class ServicioRepository(private val servicioDao: ServicioDao){
    val getAllData: LiveData<List<Servicio>> = servicioDao.getAllData()

            suspend fun addServicio(servicio: Servicio) {
                servicioDao.addServicio(servicio)
    }

            suspend fun updateServicio(servicio: Servicio) {
                servicioDao.updateServicio(servicio)
    }

            suspend fun deleteServicio(servicio: Servicio) {
                servicioDao.deleteServicio(servicio)

    }
}
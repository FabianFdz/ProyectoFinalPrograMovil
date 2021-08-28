package com.isc.petshopapp.repositorio

import androidx.lifecycle.LiveData
import com.isc.petshopapp.data.CitaDao
import com.isc.petshopapp.model.Cita

class CitaRepository (private val citaDao: CitaDao) {
    val getAllData: LiveData<List<Cita>> = citaDao.getAllData()
    suspend fun addCita(cita: Cita) {
        citaDao.addCita(cita)
    }
    suspend fun updateCita(cita: Cita) {
        citaDao.updateCita(cita)
    }
    suspend fun deleteCita(cita: Cita) {
        citaDao.deleteCita(cita)
    }
}
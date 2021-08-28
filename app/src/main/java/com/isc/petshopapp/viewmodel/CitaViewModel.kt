package com.isc.petshopapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.isc.petshopapp.data.CitaDatabase
import com.isc.petshopapp.model.Cita
import com.isc.petshopapp.repositorio.CitaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Esta clase es visible en las vistas. Aca hacemos alusi[on al repositorio, pero esta es la parte visible
class CitaViewModel (application : Application)
    : AndroidViewModel(application){

    val getAllData: LiveData<List<Cita>>
    private val repository: CitaRepository

    init{
        val citaDao= CitaDatabase.getDatabase(application).citaDao()
        repository = CitaRepository(citaDao)
        getAllData = repository.getAllData
    }
    fun addCita(cita: Cita) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCita(cita)
        }
    }
    fun deleteCita(cita: Cita) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCita(cita)
        }
    }
    fun updateCita(cita: Cita) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCita(cita)
        }
    }

}
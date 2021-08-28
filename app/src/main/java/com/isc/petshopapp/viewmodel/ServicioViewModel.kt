package com.isc.petshopapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.isc.petshopapp.model.Servicio

class ServicioViewModel(application: Application)
    : AndroidViewModel(application)  {

    val getAllData: LiveData<List<Servicio>>

    init{

        getAllData = repository.getAllData
    }

}
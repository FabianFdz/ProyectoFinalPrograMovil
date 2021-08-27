package com.isc.petshopapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.isc.petshopapp.data.ClienteDatabase
import com.isc.petshopapp.model.Cliente
import com.isc.petshopapp.repository.ClienteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClienteViewModel(application: Application)
    : AndroidViewModel(application) {
    val getAllData: LiveData<List<Cliente>>
    private val repository: ClienteRepository

    init{
        val clienteDao= ClienteDatabase.getDatabase(application).clienteDao()
        repository = ClienteRepository(clienteDao)
        getAllData = repository.getAllData
    }
    fun addCliente(cliente: Cliente) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCliente(cliente)
        }
    }
    fun deleteCliente(cliente: Cliente) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCliente(cliente)
        }
    }
    fun updateCliente(cliente: Cliente) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCliente(cliente)
        }
    }
}
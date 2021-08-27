package com.isc.petshopapp.repository

import androidx.lifecycle.LiveData
import com.isc.petshopapp.data.ClienteDao
import com.isc.petshopapp.model.Cliente

class ClienteRepository(private val clienteDao: ClienteDao) {
    val getAllData: LiveData<List<Cliente>> = clienteDao.getAllData()
    suspend fun addCliente(cliente: Cliente) {
        clienteDao.addCliente(cliente)
    }
    suspend fun updateCliente(cliente: Cliente) {
        clienteDao.updateCliente(cliente)
    }
    suspend fun deleteCliente(cliente: Cliente) {
        clienteDao.deleteCliente(cliente)
    }
}
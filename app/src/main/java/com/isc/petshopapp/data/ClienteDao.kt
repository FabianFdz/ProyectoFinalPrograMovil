package com.isc.petshopapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.isc.petshopapp.data.ClienteDao
import com.isc.petshopapp.model.Cliente

@Dao
interface ClienteDao {

    @Query("SELECT * FROM CLIENTE")
    fun getAllData() : LiveData<List<Cliente>>//Live data permite monitorear el compotamiento de
                                               //los datos contenidos en esa estructura
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCliente(cliente: Cliente)

    @Update
    suspend fun updateCliente(cliente: Cliente)

    @Delete
    suspend fun deleteCliente(cliente: Cliente)

}
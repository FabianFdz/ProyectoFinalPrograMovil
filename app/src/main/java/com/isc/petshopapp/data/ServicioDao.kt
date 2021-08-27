package com.isc.petshopapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.isc.petshopapp.model.Response
import com.isc.petshopapp.model.Servicio

@Dao
interface ServicioDao {
    @Query("SELECT * FROM servicio")
    fun getAllData() : LiveData<List<Servicio>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addServicio(servicio: Servicio)

    @Update
    suspend fun updateServicio(servicio: Servicio)

    @Delete
    suspend fun deleteServicio(servicio: Servicio)
}
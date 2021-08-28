package com.isc.petshopapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.isc.petshopapp.model.Cita


@Dao
interface CitaDao {
    @Query("SELECT * FROM CITA")
    fun getAllData() : LiveData<List<Cita>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCita(cita: Cita)

    @Update
    suspend fun updateCita(cita: Cita)

    @Delete
    suspend fun deleteCita(cita: Cita)

}
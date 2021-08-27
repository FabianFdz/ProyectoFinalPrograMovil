package com.isc.petshopapp.repositorio

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.isc.petshopapp.data.ServicioDao
import com.isc.petshopapp.model.Response
import com.isc.petshopapp.model.Servicio

class ServicioRepository(

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val servicioRef: DatabaseReference = rootRef.child("servicio")
) {
    fun getResponseFromRealtimeDatabaseUsingCallback(callback: ServicioDao) {
        servicioRef.get().addOnCompleteListener{ task ->
            val response = Response()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.servicios = result.children.map { snapShot ->
                        snapShot.getValue(Servicio::class.java)!!
                    }
                }
            } else {
                response.exception = task.exception
            }
            callback.onResponse(response)
        }

    }
}
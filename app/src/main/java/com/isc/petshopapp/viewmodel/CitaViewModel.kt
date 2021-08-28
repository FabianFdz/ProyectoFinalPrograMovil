package com.isc.petshopapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.isc.petshopapp.model.Cita
import com.isc.petshopapp.network.ApiUtils
import com.isc.petshopapp.network.CitaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//Esta clase es visible en las vistas. Aca hacemos alusi[on al repositorio, pero esta es la parte visible
class CitaViewModel (application : Application)
    : AndroidViewModel(application){

    var getAllData = MutableLiveData<List<Cita?>?>()
    var currCita = MutableLiveData<Cita?>()

    private val citaService: CitaService

    init {
        citaService = ApiUtils().getCitaService()!!
        getAllCitas()
    }

    fun getAllCitas() {
        citaService.getCitas()?.enqueue(object : Callback<List<Cita?>?> {
            override fun onResponse(call: Call<List<Cita?>?>, response: Response<List<Cita?>?>) {
                if (response.isSuccessful()) {
                    getAllData.value = response.body()?.toMutableList()
                }
            }

            override fun onFailure(call: Call<List<Cita?>?>, t: Throwable) {
                t.message?.let { Log.e("ERROR: ", it) }
            }
        })
    }

    /*fun getCita(id: String) {
        citaService.getCita(id)?.enqueue(object : Callback<List<Cita?>?> {
            override fun onResponse(call: Call<List<Cita?>?>, response: Response<List<Cita?>?>) {
                if (response.isSuccessful()) {
                    getAllData.value = response.body()?.toMutableList()
                }
            }

            override fun onFailure(call: Call<List<Cita?>?>, t: Throwable) {
                t.message?.let { Log.e("ERROR: ", it) }
            }
        })
    }*/

    fun addCita(c: Cita?, context: Context?) {
        val call: Call<Cita?>? = citaService.addCita(c)
        if (call != null) {
            call.enqueue(object : Callback<Cita?> {
                override fun onResponse(call: Call<Cita?>, response: Response<Cita?>) {
                    if (response.isSuccessful()) {
                        Toast.makeText(
                            context,
                            "Cita creado exitosamente!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Cita?>, t: Throwable) {
                    Log.e("ERROR: ", t.message!!)
                }
            })
        }
    }

    fun updateCita(c: Cita?, context: Context?) {
        val call: Call<Cita?>? = citaService.updateCita(c)
        if (call != null) {
            call.enqueue(object : Callback<Cita?> {
                override fun onResponse(call: Call<Cita?>, response: Response<Cita?>) {
                    if (response.isSuccessful()) {
                        Toast.makeText(
                            context,
                            "Cita updated successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Cita?>, t: Throwable) {
                    Log.e("ERROR: ", t.message!!)
                }
            })
        }
    }
}
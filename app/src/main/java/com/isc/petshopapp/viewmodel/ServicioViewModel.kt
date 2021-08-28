package com.isc.petshopapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.isc.petshopapp.model.Servicio
import com.isc.petshopapp.network.ApiUtils
import com.isc.petshopapp.network.ServicioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServicioViewModel(application: Application) : AndroidViewModel(application) {

  var getAllData = MutableLiveData<List<Servicio?>?>()

  private val servicioService: ServicioService

  init {
    servicioService = ApiUtils().getServicioService()!!
    getAllServicios()
  }

  fun getAllServicios() {
    servicioService.getServicios()?.enqueue(object : Callback<List<Servicio?>?> {
      override fun onResponse(call: Call<List<Servicio?>?>, response: Response<List<Servicio?>?>) {
        if (response.isSuccessful()) {
          getAllData.value = response.body()?.toMutableList()
        }
      }

      override fun onFailure(call: Call<List<Servicio?>?>, t: Throwable) {
        t.message?.let { Log.e("ERROR: ", it) }
      }
    })
  }
}
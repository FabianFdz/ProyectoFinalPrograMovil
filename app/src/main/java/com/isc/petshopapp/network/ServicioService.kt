package com.isc.petshopapp.network

import com.isc.petshopapp.model.Servicio
import retrofit2.Call
import retrofit2.http.*

interface ServicioService {
  @GET("servicios/getAll")
  fun getServicios(): Call<List<Servicio?>?>?

  @GET("servicios/get/{id}")
  fun getServicio(@Path("id") id: String?): Call<Servicio?>?

  @POST("servicios/add")
  fun addServicio(@Body servicio: Servicio?): Call<Servicio?>?

  @PUT("servicios/edit")
  fun updateServicio(@Body servicio: Servicio?): Call<Servicio?>?

  @DELETE("servicios/delete/{id}")
  fun deleteServicio(@Path("id") id: String): Call<Servicio?>?
}
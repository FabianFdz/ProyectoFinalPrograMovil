package com.isc.petshopapp.network

import com.isc.petshopapp.model.Servicio
import retrofit2.Call
import retrofit2.http.*

interface CitaService {
  @GET("citas/getAll")
  fun getCitas(): Call<List<Servicio?>?>?

  @GET("citas/get/{id}")
  fun getServicio(@Path("id") id: String?): Call<Servicio?>?

  @POST("citas/add")
  fun addServicio(@Body cita: Servicio?): Call<Servicio?>?

  @PUT("citas/edit")
  fun updateServicio(@Body cita: Servicio?): Call<Servicio?>?

  @DELETE("citas/delete/{id}")
  fun deleteServicio(@Path("id") id: String): Call<Servicio?>?
}
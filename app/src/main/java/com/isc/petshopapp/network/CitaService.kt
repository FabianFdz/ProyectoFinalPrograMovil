package com.isc.petshopapp.network

import com.isc.petshopapp.model.Cita
import retrofit2.Call
import retrofit2.http.*

interface CitaService {
  @GET("citas/getAll")
  fun getCitas(): Call<List<Cita?>?>?

  @GET("citas/get/{id}")
  fun getCita(@Path("id") id: String?): Call<Cita?>?

  @POST("citas/add")
  fun addCita(@Body cita: Cita?): Call<Cita?>?

  @PUT("citas/edit")
  fun updateCita(@Body cita: Cita?): Call<Cita?>?

  @DELETE("citas/delete/{id}")
  fun deleteCita(@Path("id") id: String): Call<Cita?>?
}
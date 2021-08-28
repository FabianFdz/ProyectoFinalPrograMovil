package com.isc.petshopapp.network

import com.isc.petshopapp.model.Cliente
import retrofit2.Call
import retrofit2.http.*


interface ClienteService {
  @GET("clientes/getAll")
  fun getClientes(): Call<List<Cliente?>?>?

  @GET("clientes/get/{id}")
  fun getCliente(@Path("id") id: String?): Call<Cliente?>?

  @POST("clientes/add")
  fun addCliente(@Body cliente: Cliente?): Call<Cliente?>?

  @PUT("clientes/edit")
  fun updateCliente(@Body cliente: Cliente?): Call<Cliente?>?

  @DELETE("clientes/delete/{id}")
  fun deleteCliente(@Path("id") id: String): Call<Cliente?>?
}
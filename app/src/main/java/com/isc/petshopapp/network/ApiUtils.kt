package com.isc.petshopapp.network

import com.jackrutorial.androidretrofit2crud.remote.ApiClient

class ApiUtils {
  val API_URL = "https://pet-shop-rest-api.herokuapp.com/"

  fun getClienteService(): ClienteService? {
    return ApiClient().getClient(API_URL)?.create(ClienteService::class.java)
  }
}
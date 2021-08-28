package com.isc.petshopapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.isc.petshopapp.model.Cliente
import com.isc.petshopapp.network.ApiUtils
import com.isc.petshopapp.network.ClienteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClienteViewModel(application: Application)
    : AndroidViewModel(application) {
    var currCliente: Cliente? = Cliente()
    private val clienteService: ClienteService

    init{
        clienteService =
            ApiUtils().getClienteService()!! // ClienteDatabase.getDatabase(application).clienteDao()
    }
    /*fun addCliente(cliente: Cliente) {
        viewModelScope.launch(Dispatchers.IO) {
            clienteService.addCliente(cliente)
        }
    }
    fun deleteCliente(cliente: Cliente) {
        viewModelScope.launch(Dispatchers.IO) {
            cliente.id?.let { clienteService.deleteCliente(it) }
        }
    }
    fun updateCliente(cliente: Cliente) {
        viewModelScope.launch(Dispatchers.IO) {
            clienteService.updateCliente(cliente)
        }
    }*/

    fun getCliente(context: Context?): Call<Cliente?>? {
        return clienteService.getCliente(FirebaseAuth.getInstance().currentUser?.email)
        //val call: Call<Cliente?>? = clienteService.getCliente(FirebaseAuth.getInstance().currentUser?.email)
       /* if (call != null) {
            call.enqueue(object : Callback<Cliente?> {
                override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                    if (response.isSuccessful()) {
                        currCliente = response.body()
                        if (currCliente?.id == "") {
                            addCliente(Cliente(id = FirebaseAuth.getInstance().currentUser?.email), context)
                        }
                        // listView.setAdapter(ClienteAdapter(this@MainActivity, R.layout.list_user, list))
                    }
                }

                override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                    t.message?.let { Log.e("ERROR: ", it) }
                }
            })
        }*/
    }

    fun addCliente(c: Cliente?, context: Context?) {
        val call: Call<Cliente?>? = clienteService.addCliente(c)
        if (call != null) {
            call.enqueue(object : Callback<Cliente?> {
                override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                    if (response.isSuccessful()) {
                        Toast.makeText(
                            context,
                            "Cliente creado exitosamente!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                    Log.e("ERROR: ", t.message!!)
                }
            })
        }
    }

    fun updateCliente(c: Cliente?, context: Context?) {
        val call: Call<Cliente?>? = clienteService.updateCliente(c)
        if (call != null) {
            call.enqueue(object : Callback<Cliente?> {
                override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                    if (response.isSuccessful()) {
                        Toast.makeText(
                            context,
                            "Cliente updated successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                    Log.e("ERROR: ", t.message!!)
                }
            })
        }
    }

    fun deleteCliente(id: String, context: Context?) {
        val call: Call<Cliente?>? = clienteService.deleteCliente(id)
        if (call != null) {
            call.enqueue(object : Callback<Cliente?> {
                override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                    if (response.isSuccessful()) {
                        Toast.makeText(
                            context,
                            "Cliente deleted successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                    Log.e("ERROR: ", t.message!!)
                }
            })
        }
    }
}
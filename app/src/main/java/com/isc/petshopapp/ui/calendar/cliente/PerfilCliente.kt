package com.isc.petshopapp.ui.calendar.cliente

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.isc.petshopapp.R
import com.isc.petshopapp.databinding.FragmentPerfilClienteBinding
import com.isc.petshopapp.model.Cliente
import com.isc.petshopapp.viewmodel.ClienteViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilCliente : Fragment() {

  private var _binding: FragmentPerfilClienteBinding? = null
  private val binding get() = _binding!!
  private lateinit var clienteViewModel: ClienteViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentPerfilClienteBinding.inflate(inflater, container, false)
    val root: View = binding.root

    clienteViewModel = ViewModelProvider(this)
      .get(ClienteViewModel::class.java)

    val context = this.context

    val getCall = clienteViewModel.getCliente(context)
    manageGetCliente(getCall)

    binding.btAgregar.setOnClickListener {
      findNavController().navigate(R.id.action_perfilCliente_to_nav_UpdateCliente)
    }

    return root
  }

  private fun manageGetCliente(getCall: Call<Cliente?>?) {
    if (getCall != null) {
      getCall.enqueue(object : Callback<Cliente?> {
        override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
          if (response.isSuccessful()) {
            clienteViewModel.currCliente = response.body()
            if (clienteViewModel.currCliente?.id == "") {
              clienteViewModel.addCliente(
                Cliente(id = FirebaseAuth.getInstance().currentUser?.email),
                context
              )
              val getCallAfterIns = clienteViewModel.getCliente(context)
              manageGetCliente(getCallAfterIns)
            } else {
              binding.etNombre.setText(clienteViewModel.currCliente?.nombre)
              binding.etApellidos.setText(clienteViewModel.currCliente?.apellidos)
              binding.etCorreo.setText(FirebaseAuth.getInstance().currentUser?.email)
              if (clienteViewModel.currCliente?.imagenPath != "") {
                binding.imagen.setImageBitmap(BitmapFactory.decodeFile(clienteViewModel.currCliente?.imagenPath))
              }
            }
          }
        }

        override fun onFailure(call: Call<Cliente?>, t: Throwable) {
          t.message?.let { Log.e("ERROR: ", it) }
        }
      })
    }
  }

}
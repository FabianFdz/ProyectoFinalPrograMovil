package com.isc.petshopapp.ui.calendar.cliente

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.isc.petshopapp.Apoyo.Apoyo
import com.isc.petshopapp.BuildConfig
import com.google.firebase.auth.FirebaseAuth
import com.isc.petshopapp.R
import com.isc.petshopapp.databinding.FragmentUpdateClienteBinding
import com.isc.petshopapp.model.Cliente
import com.isc.petshopapp.viewmodel.ClienteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UpdateCliente : Fragment() {
    private var _binding: FragmentUpdateClienteBinding?=null
    private val binding get() = _binding!!

    //Para registrar la imagen de persona
    private lateinit var tomarFotoActivity: ActivityResultLauncher<Intent>
    private lateinit var imagenFile: File

    private lateinit var clienteViewModel: ClienteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateClienteBinding.inflate(inflater,
            container,false)
        val root: View = binding.root

        clienteViewModel = ViewModelProvider(this)
            .get(ClienteViewModel::class.java)


    clienteViewModel = ViewModelProvider(this)
      .get(ClienteViewModel::class.java)

    val context = this.context

    val getCall = clienteViewModel.getCliente(context)
    manageGetCliente(getCall)

    binding.btAgregar.setOnClickListener { insertaCliente() }
    binding.btFoto.setOnClickListener { tomarFoto() }
    binding.btRotateLeft.setOnClickListener {
      binding.imagen.rotation = binding.imagen.rotation + 90f
    }
    binding.btRotateRight.setOnClickListener {
      binding.imagen.rotation = binding.imagen.rotation - 90f
    }

    tomarFotoActivity =
      registerForActivityResult(ActivityResultContracts.StartActivityForResult())
      { result ->
        if (result.resultCode == Activity.RESULT_OK) {
          binding.imagen.setImageBitmap(BitmapFactory.decodeFile(imagenFile?.absolutePath))
        }
      }

    return root
  }

  private fun manageGetCliente (getCall: Call<Cliente?>?) {
    if (getCall != null) {
      getCall.enqueue(object : Callback<Cliente?> {
        override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
          if (response.isSuccessful()) {
            clienteViewModel.currCliente = response.body()
            if (clienteViewModel.currCliente?.id == "") {
              clienteViewModel.addCliente(Cliente(id = FirebaseAuth.getInstance().currentUser?.email), context)
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

  @SuppressLint("QueryPermissionsNeeded")
  private fun tomarFoto() {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (intent.resolveActivity(requireContext().packageManager) != null) {
      imagenFile = Apoyo.createImageFile(requireContext())
      val photoURI =
        FileProvider.getUriForFile(
          requireContext(), BuildConfig.APPLICATION_ID + ".provider",
          imagenFile!!
        )
      intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
      tomarFotoActivity.launch(intent)
    }

  }

  private fun insertaCliente() {
    val nombre = binding.etNombre.text.toString()
    val id = FirebaseAuth.getInstance().currentUser?.email
    val apellidos = binding.etApellidos.text.toString()
    if (validos(nombre, apellidos)) {
      var cliente: Cliente
      if (imagenFile !== null) {
        cliente = Cliente(id, imagenFile?.absolutePath, nombre, apellidos)
      } else {
        cliente = Cliente(id, "", nombre, apellidos)
      }
      clienteViewModel.updateCliente(cliente, this.context)
      Toast.makeText(
        requireContext(), getString(R.string.clienteadd),
        Toast.LENGTH_LONG
      ).show()
      //findNavController().navigate(R.id.action_nav_UpdateCliente_to_nav_ListCliente)
    } else {
      Toast.makeText(
        requireContext(), getString(R.string.clientefail),
        Toast.LENGTH_LONG
      ).show()
    }
  }

  private fun validos(nombre: String?, apellidos: String?): Boolean {
    if (nombre != null && apellidos != null) {
      return !(nombre.isEmpty() || apellidos.isEmpty())
    }
    return false
  }
}
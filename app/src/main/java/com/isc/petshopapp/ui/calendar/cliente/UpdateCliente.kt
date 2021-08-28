package com.isc.petshopapp.ui.calendar.cliente

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
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

    val getCall = clienteViewModel.getCliente()
    manageGetCliente(getCall)

    binding.btAgregar.setOnClickListener {
        subeFoto()
    }
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

    private fun subeFoto() {
        if (imagenFile!= null && imagenFile!!.exists() && imagenFile!!.canRead()) {
            //Defino el nombre de la imagen donde quedará en el Storage
            val nombreArchivo = Apoyo.nombreSeguro("imagen_","jpg")

            //Obtengo la ruta de donde se grabó la imagen tomada
            val ruta = Uri.fromFile(imagenFile)

            //Genero la referenia en el Storage
            val referencia : StorageReference =
                Firebase.storage.reference.child("imagenes/$nombreArchivo")

            //Se inicia la subida del archivo al Firestore Storage
            val subidaArchivo = referencia.putFile(ruta)

            subidaArchivo.addOnSuccessListener {
                val rutaImagenFirebase = referencia.downloadUrl
                rutaImagenFirebase.addOnSuccessListener {
                    Apoyo.muestraTexto(requireContext(),"Imagen subida...")
                    val rutaImagen = it.toString()
                    insertaCliente(rutaImagen)  //Se almacenará la info en Sqlite... local
                }
            }
            subidaArchivo.addOnFailureListener() {
                Apoyo.muestraTexto(requireContext(),"Error subiendo imagen...")
                insertaCliente("Imagen no disponible")
            }
        } else {  //Si no existe o no se puede leer
            insertaCliente("Imagen no disponible")
        }

    }

    private fun manageGetCliente (getCall: Call<Cliente?>?) {
    if (getCall != null) {
      getCall.enqueue(object : Callback<Cliente?> {
        override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
          if (response.isSuccessful()) {
            clienteViewModel.currCliente = response.body()
            if (clienteViewModel.currCliente?.id == "") {
              clienteViewModel.addCliente(Cliente(id = FirebaseAuth.getInstance().currentUser?.email), context)
              val getCallAfterIns = clienteViewModel.getCliente()
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

  private fun insertaCliente(rutaFoto:String) {
    val nombre = binding.etNombre.text.toString()
    val id = FirebaseAuth.getInstance().currentUser?.email
    val apellidos = binding.etApellidos.text.toString()
    if (validos(nombre, apellidos)) {
      var cliente: Cliente
      if (imagenFile !== null) {
        cliente = Cliente(id, rutaFoto, nombre, apellidos)
      } else {
        cliente = Cliente(id, "", nombre, apellidos,)
      }
      clienteViewModel.updateCliente(cliente, this.context)
      Toast.makeText(
        requireContext(), getString(R.string.clienteadd),
        Toast.LENGTH_LONG
      ).show()
      findNavController().navigate(R.id.action_nav_UpdateCliente_to_perfilCliente)
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
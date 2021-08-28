package com.isc.petshopapp.ui.calendar.cliente

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
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
import com.isc.petshopapp.R
import com.isc.petshopapp.databinding.FragmentUpdateClienteBinding
import com.isc.petshopapp.model.Cliente
import com.isc.petshopapp.viewmodel.ClienteViewModel
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

<<<<<<< HEAD
        binding.btAgregar.setOnClickListener { insertaCliente() }
=======
        binding.btFoto.setOnClickListener { tomarFoto() }
        binding.btRotateLeft.setOnClickListener {
            binding.imagen.rotation = binding.imagen.rotation + 90f
        }
        binding.btRotateRight.setOnClickListener {
            binding.imagen.rotation = binding.imagen.rotation - 90f
        }

        tomarFotoActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    binding.imagen.setImageBitmap(BitmapFactory.decodeFile(imagenFile.absolutePath))
                }
            }
>>>>>>> 8fe0e69cd57bbe56b6633a80bfe81b3107cda971

        return root
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun tomarFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                imagenFile = Apoyo.createImageFile(requireContext())
                val photoURI =
                    FileProvider.getUriForFile(
                        requireContext(), BuildConfig.APPLICATION_ID + ".provider",
                        imagenFile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                tomarFotoActivity.launch(intent)
            }

    }

    private fun insertaCliente() {
        val cedula= binding.etCedula.text.toString()
        val nombre= binding.etNombre.text.toString()
        val apellidos=binding.etApellidos.text.toString()
        val edad=   binding.etEdad.text.toString()
        if (validos(cedula,nombre,apellidos,edad)) {
            val cliente = Cliente(0,cedula,nombre,apellidos,edad.toInt())
            clienteViewModel.addCliente(cliente)
            Toast.makeText(requireContext(),getString(R.string.clienteadd),
                Toast.LENGTH_LONG).show()
            //findNavController().navigate(R.id.action_nav_UpdateCliente_to_nav_ListCliente)
        } else {
            Toast.makeText(requireContext(),getString(R.string.clientefail),
                Toast.LENGTH_LONG).show()
        }
    }

    private fun validos(cedula: String, nombre: String, apellidos: String, edad: String): Boolean {
        return !(cedula.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || edad.isEmpty())
    }
}
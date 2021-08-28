package com.isc.petshopapp.ui.calendar.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.isc.petshopapp.R
import com.isc.petshopapp.databinding.FragmentUpdateClienteBinding
import com.isc.petshopapp.model.Cliente
import com.isc.petshopapp.viewmodel.ClienteViewModel

class UpdateCliente : Fragment() {
    private var _binding: FragmentUpdateClienteBinding?=null
    private val binding get() = _binding!!

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

        binding.btAgregar.setOnClickListener { insertaCliente() }

        return root
    }

    private fun insertaCliente() {
        val cedula= binding.etCedula.text.toString()
        val nombre= binding.etNombre.text.toString()
        val apellidos=binding.etApellidos.text.toString()
        val edad=binding.etEdad.text.toString()
        if (validos(cedula,nombre,apellidos,edad)) {
            val cliente = Cliente(0,cedula,nombre,apellidos,edad.toInt())
            clienteViewModel.addCliente(cliente)
            Toast.makeText(requireContext(),getString(R.string.clienteadd),
                Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_nav_UpdateCliente_to_nav_ListCliente)
        } else {
            Toast.makeText(requireContext(),getString(R.string.clientefail),
                Toast.LENGTH_LONG).show()
        }
    }

    private fun validos(cedula: String, nombre: String, apellidos: String, edad: String): Boolean {
        return !(cedula.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || edad.isEmpty())
    }
}
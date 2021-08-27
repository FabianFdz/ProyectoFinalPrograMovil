package com.isc.petshopapp.ui.calendar.cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.isc.petshopapp.R
import com.isc.petshopapp.databinding.FragmentPerfilClienteBinding

class PerfilCliente : Fragment() {

    private var _binding: FragmentPerfilClienteBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilClienteBinding.inflate(inflater,container,false)
        val root: View = binding.root

        binding.btAgregar.setOnClickListener{
            findNavController().navigate(R.id.action_perfilCliente_to_nav_UpdateCliente)
        }


        return root
    }

}
package com.isc.petshopapp.ui.citas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.isc.petshopapp.R
import com.isc.petshopapp.databinding.FragmentUpdateCitaBinding
import com.isc.petshopapp.model.Cita
import com.isc.petshopapp.viewmodel.CitaViewModel


class UpdateCita : Fragment() {
    private var _binding: FragmentUpdateCitaBinding?=null
    private val binding get() = _binding!!

    private lateinit var citaViewModel: CitaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateCitaBinding.inflate(inflater,
            container,false)
        val root: View = binding.root

        citaViewModel = ViewModelProvider(this)
            .get(CitaViewModel::class.java)

        binding.btAgregar.setOnClickListener { insertaCita() }

        return root
    }

    private fun insertaCita() {
        val id =binding.etId.text.toString()
        val descripcion =binding.etDescripcion.text.toString()
        val titulo=binding.etTitulo.text.toString()
        val fecha=binding.etFecha.text.toString()
        val inicio=binding.etInicio.text.toString()
        val fin=binding.etFin.text.toString()
        if (validos(id,descripcion,titulo,fecha,inicio,fin)) {
            val cita = Cita(0,descripcion,titulo,fecha,inicio,fin)
            citaViewModel.addCita(cita)
            Toast.makeText(requireContext(),getString(R.string.citaadd),
                Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_nav_UpdateCita_to_nav_ListCita)
        } else {
            Toast.makeText(requireContext(),getString(R.string.citafail),
                Toast.LENGTH_LONG).show()
        }
    }

    private fun validos(id: String, descripcion: String, titulo: String, fecha: String, inicio: String, fin: String): Boolean {
        return !(id.isEmpty() || descripcion.isEmpty() || titulo.isEmpty() || fecha.isEmpty() || inicio.isEmpty() || fin.isEmpty())
    }


}
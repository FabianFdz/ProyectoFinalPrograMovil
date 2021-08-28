package com.isc.petshopapp.ui.citas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.isc.petshopapp.R
import com.isc.petshopapp.adapter.CitaAdapter
import com.isc.petshopapp.databinding.FragmentListCitaBinding
import com.isc.petshopapp.viewmodel.CitaViewModel


class ListCita :  Fragment() {
    private var _binding: FragmentListCitaBinding?=null
    private val binding get() = _binding!!


    private lateinit var citaViewModel: CitaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListCitaBinding.inflate(inflater,
            container,false)
        val root: View = binding.root

        val citaAdapter = CitaAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter = citaAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())


        citaViewModel = ViewModelProvider(this)
            .get(CitaViewModel::class.java)


        citaViewModel.getAllData.observe(viewLifecycleOwner,{
                citas -> citaAdapter.setData(citas)})


        return root
    }
}
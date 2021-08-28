package com.isc.petshopapp.ui.servicio

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.isc.petshopapp.R
import com.isc.petshopapp.adapter.ServicioAdapter
import com.isc.petshopapp.databinding.FragmentServicio01Binding
import com.isc.petshopapp.model.Response
import com.isc.petshopapp.viewmodel.ServicioViewModel


class Servicio01 : Fragment() {

    private var _binding: FragmentServicio01Binding?=null
    private val binding get() = _binding!!

    private lateinit var myRecyclerView: RecyclerView
    private lateinit var myRef: DatabaseReference

    private lateinit var servicioViewModel: ServicioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentServicio01Binding.inflate(inflater,
            container,false)
        val root: View = binding.root

        //Activamos el recycler View (reciclador)
        val servicioAdapter = ServicioAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter = servicioAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())

        //llenado de recyclerView
        //Obtener la info del collection service vía el ServicioViewModel
        servicioViewModel = ViewModelProvider(this)
            .get(ServicioViewModel::class.java)

        //Ojo cómo se define la manera de actualzar...
        servicioViewModel.getAllData.observe(viewLifecycleOwner,{
                servicios -> servicioAdapter.setData(servicios)})

        return root
    }

    //genera logs del nombre de los productos o los errores
    //aqui es donde se jala la info
    private fun print(response: Response) {
        response.servicios?.let { servicios ->
            servicios.forEach{ servicio ->
                servicio.nombre?.let {
                    Log.i("RealtimeDatabaseTag", it)
                }
            }
        }

        response.exception?.let { exception ->
            exception.message?.let {
                Log.e(TAG, it)
            }
        }
    }

}
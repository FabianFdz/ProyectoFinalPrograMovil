package com.isc.petshopapp.ui.servicio

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.isc.petshopapp.R
import com.isc.petshopapp.adapter.ServicioAdapter
import com.isc.petshopapp.databinding.FragmentServicio01Binding
import com.isc.petshopapp.model.Response


class Servicio01 : Fragment() {

    private var _binding: FragmentServicio01Binding?=null
    private val binding get() = _binding!!

    private lateinit var myRecyclerView: RecyclerView
    private lateinit var myRef: DatabaseReference

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

        myRef = Firebase.database.getReference("servicio")
        myRef.child("0").get().addOnSuccessListener {
            if(it.exists()){
                val nombre = it.child("nombre").value
                val descripcion = it.child("descripcion").value
                val imgUrl = it.child("imgUrl").value
                val precio = it.child("precio").value

            }
        }.addOnFailureListener{
            //Toast.makeText(this,"There are no services",Toast.LENGTH_SHORT).show()
        }



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
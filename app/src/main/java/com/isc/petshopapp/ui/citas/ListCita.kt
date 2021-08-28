package com.isc.petshopapp.ui.citas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.isc.petshopapp.R
import com.isc.petshopapp.databinding.FragmentListCitaBinding


class ListCita : Fragment() {

    private var _binding: FragmentListCitaBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListCitaBinding.inflate(inflater,
            container,false)
        val root: View = binding.root

        //binding.btAddCita.setOnClickListener {
          //  findNavController().navigate(R.id.action_nav_ListCita_to_nav_UpdateCita)
        //}

        return root
    }

}
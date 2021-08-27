package com.isc.petshopapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.isc.petshopapp.databinding.FragmentProductosBinding


class productosFragment : Fragment() {

  private var _binding: FragmentProductosBinding?=null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentProductosBinding.inflate(inflater,
      container,false)
    val root: View = binding.root
    return root
  }


}
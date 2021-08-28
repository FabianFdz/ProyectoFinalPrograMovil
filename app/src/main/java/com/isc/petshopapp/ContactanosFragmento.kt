package com.isc.petshopapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.isc.petshopapp.databinding.FragmentContactanosBinding


class ContactanosFragmento : Fragment() {

    private var _binding: FragmentContactanosBinding? = null
    private val binding get() = _binding!!
    private var conPermisos:Boolean=true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactanosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btMapa.setOnClickListener {
            obtenerCoordenadas()
        }

        val sitio = "https://www.petshop.co.uk/"
        binding.btWeb.setOnClickListener {
            openSite(sitio)
        }

        return root
    }

    private fun openSite(sitio: String) {
        val webpage = Uri.parse(sitio);
        val intent = Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }

    private fun obtenerCoordenadas() {

        val fusedLocationClient: FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), 105)
        }
        if (conPermisos) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        val latitud ="" + location.getLatitude()
                        val Longitud ="" + location.getLongitude()
                        val Altura="" + location.getAltitude()

                        //Para ubicarse en el mapa
                        val location = Uri.parse("geo:" + latitud +
                                "," + Longitud +
                                "?z=18")
                        val mapIntent = Intent(Intent.ACTION_VIEW, location)
                        startActivity(mapIntent)

                    } else {

                    }
                }

        }


    }


}
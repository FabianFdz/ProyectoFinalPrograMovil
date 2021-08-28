package com.isc.petshopapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.isc.petshopapp.databinding.CitaFilaBinding
import com.isc.petshopapp.model.Cita
import com.isc.petshopapp.ui.citas.ListCitaDirections

class CitaAdapter: RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    private var listaCitas = emptyList<Cita?>()

    inner class CitaViewHolder(private val itemBinding: CitaFilaBinding):
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(cita: Cita) {
            itemBinding.tvTitulo.text = cita.titulo
            itemBinding.tvDescripcion.text = cita.descripcion
            itemBinding.tvFecha.text = cita.fecha
            itemBinding.vistaFila.setOnClickListener {
                //val action = ListCitaDirections.actionNavListCitaToNavUpdateCita(cita)
                //itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val itemBinding = CitaFilaBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        return CitaViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val citaActual = listaCitas[position]
        if (citaActual != null) {
            holder.bind(citaActual)
        }
    }

    override fun getItemCount(): Int {
        return listaCitas.size
    }

    fun setData(citas: List<Cita?>?) {
        if (citas != null) {
            this.listaCitas=citas
        }
        notifyDataSetChanged() //Redibuja el adapter...
    }


}
package com.isc.petshopapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isc.petshopapp.databinding.ServicioFilaBinding
import com.isc.petshopapp.model.Servicio
import com.bumptech.glide.Glide

class ServicioAdapter: RecyclerView.Adapter<ServicioAdapter.ServioViewHolder>(){
    //Se define la lista que se usa para contener la información de las personas (de la tabla)
    private var listaServicios = emptyList<Servicio>()


    //contenedor de vistas
    inner class ServioViewHolder(private val itemBinding: ServicioFilaBinding):
        RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(servicio: Servicio){
                //aca se van llenando la data de las cajitas con lo q hay en la clase Servicio
                itemBinding.tvNombre.text = servicio.nombre
                itemBinding.tvResumen.text = servicio.descripcion
                itemBinding.tvPrecio.text = servicio.precio.toString()

                Glide.with(itemBinding.root.context)
                    .load(servicio.imgUrl)
                    .circleCrop()
                    .into(itemBinding.filaImagen)

            }


        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServioViewHolder {
        val itemBinding = ServicioFilaBinding
            .inflate(LayoutInflater.from(parent.context),
                parent,
                false)

        return ServioViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: ServioViewHolder, position: Int) {
        //para poner valor a la caja
        val servicioActual = listaServicios[position]
        holder.bind(servicioActual)

    }

    override fun getItemCount(): Int {
        return listaServicios.size

    }

    fun setData(servicios: List<Servicio>) {
        this.listaServicios=servicios
        notifyDataSetChanged() //Redibuja el adapter... o actualiza todo...
    }

}
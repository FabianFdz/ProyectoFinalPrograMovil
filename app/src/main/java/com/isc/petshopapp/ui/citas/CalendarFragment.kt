package com.isc.petshopapp.ui.citas

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.isc.petshopapp.R
import com.isc.petshopapp.databinding.FragmentCalendarBinding
import com.isc.petshopapp.model.Cita
import com.isc.petshopapp.viewmodel.CitaViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private var mes: Int=0
    private var dia: Int=0
    private var anno: Int=0
    private var hora_fin: Int=0
    private var minu_fin: Int=0
    private var hora_ini: Int=0
    private var minu_ini: Int=0
    val tipo = 0

    val local: LocalDate = LocalDate.now()
    private lateinit var citaViewModel: CitaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        citaViewModel = ViewModelProvider(this)
            .get(CitaViewModel::class.java)

        mes = local.getMonthValue()
        dia = local.getDayOfMonth()
        anno = local.getYear()
        binding.btFecha.setText("" + dia.toString() + "/" + mes.toString() + "/" + anno)

        binding.btFecha.setOnClickListener { obtenerFecha() }
        binding.btHoraIni.setOnClickListener { obtenerHora(1) }
        binding.btHoraFin.setOnClickListener { obtenerHora(2) }
        binding.btCrearEvento.setOnClickListener { CrearEvento() }
        binding.cbTodoDia.setOnClickListener { fijarHora() }

        return root
    }

    fun fijarHora() {
        if (this.binding.cbTodoDia.isChecked) {
            binding.btHoraIni.text="08:00 a.m."
            binding.btHoraFin.text="05:00 p.m."
            binding.btHoraIni.setEnabled(false)
            binding.btHoraFin.setEnabled(false)
        } else {
            var hora24: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            var AM_PM: String
            AM_PM = if (hora24 < 12) {"a.m."} else {"p.m."}
            var hora = Calendar.getInstance().get(Calendar.HOUR)
            binding.btHoraIni.text="" + (if (hora < 10) "0$hora" else hora) + ":00 " + AM_PM
            hora24++
            hora++
            AM_PM = if (hora24 < 12) {"a.m."} else {"p.m."}
            binding.btHoraFin.text="" + (if (hora < 10) "0$hora" else hora) + ":00 " + AM_PM
            binding.btHoraIni.setEnabled(true)
            binding.btHoraFin.setEnabled(true)
        }
    }

    fun obtenerFecha() {
        val local = LocalDate.now()
        mes = local.monthValue-1
        dia = local.dayOfMonth
        anno = local.year

        val recogerFecha = DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth -> //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                anno = year
                mes = month+1
                dia = dayOfMonth
                binding.btFecha.setText("" + dia.toString() + "/" + mes.toString() + "/" + anno)
            }, anno, mes, dia
        )
        recogerFecha.show()
    }

    fun obtenerHora(tipo: Int) {
        val localTime: LocalTime = LocalTime.now()
        //Variables para obtener la fecha actual
        val hora: Int = localTime.getHour()
        val minuto: Int = localTime.getMinute()

        val recogerHora = TimePickerDialog(context,
            { view, hora, minuto ->
                val strHora = "" + if (hora < 10) "0$hora" else hora
                val strminuto = "" + if (minuto < 10) "0$minuto" else minuto
                val AM_PM: String
                AM_PM = if (hora < 12) {
                    "a.m."
                } else {
                    "p.m."
                }

                if (tipo === 1) {
                    hora_ini = hora
                    minu_ini = minuto
                    binding.btHoraIni.setText("$strHora:$strminuto $AM_PM")
                } else {
                    hora_fin = hora
                    minu_fin = minuto
                    binding.btHoraFin.setText("$strHora:$strminuto $AM_PM")
                }
            }, hora, minuto, false
        )
        recogerHora.show()
    }

    fun CrearEvento() {
        var titulo=binding.etTitulo.text.toString()
        var descripcion=binding.etDescripcion.text.toString()
        if (titulo.isNotEmpty() && descripcion.isNotEmpty()) {
            val calendarIntent = Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI)
            val beginTime = Calendar.getInstance()
            val endTime = Calendar.getInstance()

            beginTime[anno, mes - 1, dia, hora_ini] = minu_ini
            endTime[anno, mes - 1, dia, hora_fin] = minu_fin

            val inicio =beginTime.toString()
            val fin = endTime.toString()
            val fecha = binding.btFecha.setText("" + dia.toString() + "/" + mes.toString() + "/" + anno)

            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
            calendarIntent.putExtra(CalendarContract.Events.TITLE, binding.etTitulo.text.toString())
            calendarIntent.putExtra(Intent.EXTRA_EMAIL, FirebaseAuth.getInstance().currentUser?.email)
            calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, descripcion)

            val cita = Cita("",descripcion,titulo,"" + dia.toString() + "/" + mes.toString() + "/" + anno,
                binding.btHoraIni.text.toString(),binding.btHoraFin.text.toString())
            citaViewModel.addCita(cita, context)

            startActivity(calendarIntent)
            findNavController().navigate(R.id.action_nav_calendar_to_nav_ListCita)

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
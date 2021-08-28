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
import com.isc.petshopapp.databinding.FragmentCalendarBinding
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mes = local.getMonthValue()
        dia = local.getDayOfMonth()
        anno = local.getYear()
        binding.btFecha.setText("" + dia.toString() + "/" + mes.toString() + "/" + anno)

        binding.btFecha.setOnClickListener { obtenerFecha() }
        binding.btHoraIni.setOnClickListener { obtenerHora() }
        binding.btHoraFin.setOnClickListener { obtenerHora() }
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

    fun obtenerHora() {
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

            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
            calendarIntent.putExtra(CalendarContract.Events.TITLE, binding.etTitulo.text.toString())
            calendarIntent.putExtra(Intent.EXTRA_EMAIL, "test@mail.com")
            calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, descripcion)

            startActivity(calendarIntent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
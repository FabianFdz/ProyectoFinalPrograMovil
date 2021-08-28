package com.isc.petshopapp.Apoyo

import android.content.Context
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Apoyo {
    companion object {
        fun nombreSeguro(prefijo: String, sufijo: String): String {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            return if (sufijo.isEmpty())
                "$prefijo$timeStamp"
            else "$prefijo$timeStamp.$sufijo"
        }

        fun createImageFile(contexto: Context): File {
            val imageFileName = nombreSeguro("cliente_", "")
            val storageDir: File? = contexto.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(imageFileName, ".jpg", storageDir)
        }

        fun muestraTexto(context: Context, texto: String) {
            Toast.makeText(context, texto, Toast.LENGTH_LONG).show()
        }
    }
}
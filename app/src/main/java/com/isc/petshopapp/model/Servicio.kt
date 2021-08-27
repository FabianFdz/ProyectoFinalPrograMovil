package com.isc.petshopapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicio")
data class Servicio(
    @PrimaryKey(autoGenerate = true)
    val Id : Int,
    @ColumnInfo(name="nombre")
    val nombre: String,
    @ColumnInfo(name="descripcion")
    var descripcion: String,
    @ColumnInfo(name="imgUrl")
    var imgUrl : String,
    @ColumnInfo(name="precio")
    var precio: Int

)


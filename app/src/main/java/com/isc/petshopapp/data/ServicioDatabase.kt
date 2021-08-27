package com.isc.petshopapp.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.isc.petshopapp.model.Servicio

@Database(entities=[Servicio::class], version = 1,exportSchema = false)
abstract class ServicioDatabase:RoomDatabase() {
    abstract fun servicioDao(): ServicioDao
    companion object{
        @Volatile
        private var INSTANCE: ServicioDatabase?= null

        fun getDatabase(context: android.content.Context): ServicioDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ServicioDatabase::class.java,
                    "servicio_database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }

    }
}
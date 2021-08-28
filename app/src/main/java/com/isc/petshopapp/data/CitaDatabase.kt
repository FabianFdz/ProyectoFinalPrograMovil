package com.isc.petshopapp.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.isc.petshopapp.model.Cita

@Database (entities=[Cita::class], version = 1,exportSchema = false)
abstract class CitaDatabase: RoomDatabase() {

    abstract fun citaDao(): CitaDao
    companion object {
        @Volatile
        private var INSTANCE: CitaDatabase? = null

        fun getDatabase(context: android.content.Context): CitaDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CitaDatabase::class.java,
                    "cita_database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }

}
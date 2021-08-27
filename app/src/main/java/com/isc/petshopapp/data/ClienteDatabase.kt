package com.isc.petshopapp.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.isc.petshopapp.model.Cliente

@Database(entities=[Cliente::class], version = 1,exportSchema = false)
abstract class ClienteDatabase: RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    companion object {
        @Volatile
        private var INSTANCE: ClienteDatabase? = null

        fun getDatabase(context: android.content.Context): ClienteDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClienteDatabase::class.java,
                    "cliente_database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}
package com.elaniin.technical_test.databases.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elaniin.technical_test.databases.room.entities.PokemonTeamEntity

@Database(entities = [PokemonTeamEntity::class], version = 1, exportSchema = false)
abstract class PokemonTeamDatabase : RoomDatabase() {

    abstract fun pokemonTeamDao(): PokemonTeamDao

    companion object {
        @Volatile
        private var INSTANCE: PokemonTeamDatabase? = null

        fun getDatabase(context: Context): PokemonTeamDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonTeamDatabase::class.java, "pokemon"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
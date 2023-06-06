package com.elaniin.technical_test.databases.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elaniin.technical_test.databases.room.entities.PokemonTeamEntity

@Dao
interface PokemonTeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(pokemonTeam: PokemonTeamEntity)

    @Query("SELECT * FROM pokemon_team_entity")
    fun getAll(): List<PokemonTeamEntity?>?

    @Query("DELETE FROM pokemon_team_entity WHERE team_name LIKE '%' || :id || '%' AND name LIKE '%' || :name || '%'")
    fun deleteTeam(id: Int?, name: String?)

}
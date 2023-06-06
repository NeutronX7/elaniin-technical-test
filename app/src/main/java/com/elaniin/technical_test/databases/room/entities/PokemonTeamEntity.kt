package com.elaniin.technical_test.databases.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_team_entity")
data class PokemonTeamEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name = "team_name")
    val teamName: String?,
    @ColumnInfo(name = "pokemon_name")
    val pokemonName: String?,
    @ColumnInfo(name = "pokemon_number")
    val pokemonNumber: Int?,
    @ColumnInfo(name = "pokemon_description")
    val pokemonDescription: String?,
    @ColumnInfo(name = "pokemon_type")
    val pokemonType: String?,
    @ColumnInfo(name = "generated_key")
    val generatedKey: String?,
)

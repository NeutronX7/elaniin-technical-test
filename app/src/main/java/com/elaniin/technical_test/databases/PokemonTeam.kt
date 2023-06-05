package com.elaniin.technical_test.databases

data class PokemonTeam(
    val pokemonName: String,
    val pokemonNumber: Int,
    val pokemonType: List<String>,
    val pokemonDescription: String,
    val pokemonPhoto: String
)